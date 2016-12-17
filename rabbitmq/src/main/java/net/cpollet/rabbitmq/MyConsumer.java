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

/**
 * Created by cpollet on 09.12.16.
 */
class MyConsumer implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MyConsumer.class);

    private final Channel channel;
    private final String queueName;
    private final Set<Object> alreadyRejected;

    MyConsumer(String exchangeName, String name) throws Exception {
        alreadyRejected = new HashSet<>();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        String deadExchangeName = exchangeName + "_dead-" + name;

        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare(deadExchangeName, BuiltinExchangeType.DIRECT);

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", deadExchangeName);

        queueName = channel.queueDeclare("queue-" + name, false, false, false, args).getQueue();
        channel.queueBind(queueName, exchangeName, "");

        String deadQueueName = channel.queueDeclare("deadQueue-" + name, false, false, false, null).getQueue();
        channel.queueBind(deadQueueName, deadExchangeName, "");
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
            channel.basicConsume(queueName, false, consumer);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
