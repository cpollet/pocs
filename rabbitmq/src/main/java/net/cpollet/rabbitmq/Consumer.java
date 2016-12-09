package net.cpollet.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by cpollet on 09.12.16.
 */
class Consumer implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Consumer.class);

    private final String name;
    private final Channel channel;
    private final String exchangeName;
    private final String queueName;

    Consumer(String exchangeName, String name) throws Exception {
        this.exchangeName = exchangeName;
        this.name = name;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);


        queueName = channel.queueDeclare("queue-"+name, false, false, false, null).getQueue();
        // queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, "");
    }

    @Override
    public void run() {
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                LOGGER.info("{} received {}", name, message);
            }
        };

        try {
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            // ignore
        }
    }
}
