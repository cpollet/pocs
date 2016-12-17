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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * Created by cpollet on 09.12.16.
 */
class MyConsumer implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MyConsumer.class);

    private final Channel channel;
    private final Set<Object> alreadyRejected;
    private final Configuration configuration;

    public MyConsumer(Configuration configuration) throws IOException, TimeoutException {
        this.configuration = configuration;
        alreadyRejected = new HashSet<>();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        channel = connection.createChannel();
        channel.exchangeDeclare(configuration.getExchangeName(), BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare(configuration.getDeadExchangeName(), BuiltinExchangeType.DIRECT);

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", configuration.getDeadExchangeName());

        channel.queueDeclare(configuration.getQueueName(), false, false, false, args);
        channel.queueBind(configuration.getQueueName(), configuration.getExchangeName(), "");

        channel.queueDeclare(configuration.getDeadQueueName(), false, false, false, null);
        channel.queueBind(configuration.getDeadQueueName(), configuration.getDeadExchangeName(), "");
    }

    @Override
    public void run() {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                if (!acceptMessage(message)) {
                    LOGGER.info("reject {}", message);
                    alreadyRejected.add(message);
                    channel.basicReject(envelope.getDeliveryTag(), false);
                } else {
                    LOGGER.info("ack    {}", message);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }

            private boolean acceptMessage(String message) {
                if (value(message) % 5 != 0) {
                    return true;
                } else if (!alreadyRejected.contains(message)) {
                    return false;
                }

                return Math.random() > 0.5D;
            }

            private Long value(String message) {
                try {
                    return Long.valueOf(message);
                } catch (Exception e) {
                    LOGGER.error(e);
                    return 0L;
                }
            }
        };

        try {
            channel.basicConsume(configuration.getQueueName(), false, consumer);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
