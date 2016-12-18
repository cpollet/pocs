package net.cpollet.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * Created by cpollet on 09.12.16.
 */
class MyConsumer implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MyConsumer.class);

    private final Channel channel;
    private final Configuration configuration;
    private final String retryExchange;

    public MyConsumer(Configuration configuration) throws IOException, TimeoutException {
        this.configuration = configuration;
        this.retryExchange = configuration.getQueueName() + ".retry";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        channel = connection.createChannel();

        channel.exchangeDeclare(retryExchange, BuiltinExchangeType.FANOUT);

        channel.queueDeclare(configuration.getQueueName(), false, false, false, null);
        channel.queueBind(configuration.getQueueName(), configuration.getExchangeName(), "");
        channel.queueBind(configuration.getQueueName(), retryExchange, "");
    }

    @Override
    public void run() {
        Consumer consumer = new DefaultConsumer(channel) {
            private static final String RETRY_COUNT_HEADER = "x-retry-count";

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                Integer retryCount = retryCount(properties);

                if (acceptMessage(message)) {
                    LOGGER.info("ack    {} ({})", message, retryCount);
                    return;
                }

                LOGGER.info("reject {} ({})", message, retryCount);

                if (retryCount > 2) {
                    LOGGER.info("drop   {} ({})", message, retryCount);
                    return;
                }

                retryCount++;
                Long timeout = (long) Math.pow(2, retryCount) * 1000L;

                HashMap<String, Object> headers = new HashMap<>();
                headers.put(RETRY_COUNT_HEADER, retryCount);

                AMQP.BasicProperties.Builder propertiesBuilder = new AMQP.BasicProperties().builder();
                propertiesBuilder.headers(headers);

                channel.basicPublish("", queue(timeout), propertiesBuilder.build(), body);
            }

            private boolean acceptMessage(String message) {
                if (value(message) < 0) {
                    return false;
                } else if (value(message) % 5 != 0) {
                    return true;
                }

                return Math.random() > 0.8D;
            }

            private Long value(String message) {
                try {
                    return Long.valueOf(message);
                } catch (Exception e) {
                    LOGGER.error(e);
                    return 0L;
                }
            }

            private Integer retryCount(AMQP.BasicProperties properties) {
                if (properties.getHeaders() == null || !properties.getHeaders().containsKey(RETRY_COUNT_HEADER)) {
                    return 0;
                }

                return (Integer) properties.getHeaders().get(RETRY_COUNT_HEADER);
            }

            private String queue(long timeout) throws IOException {
                String queueName = configuration.getQueueName() + ".retry." + timeout;

                HashMap<String, Object> args = new HashMap<>();
                args.put("x-message-ttl", timeout);
                args.put("x-dead-letter-exchange", retryExchange);
                channel.queueDeclare(queueName, false, false, false, args);

                return queueName;
            }
        };

        try {
            channel.basicConsume(configuration.getQueueName(), true, consumer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
