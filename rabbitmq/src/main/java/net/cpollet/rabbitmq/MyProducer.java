package net.cpollet.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by cpollet on 09.12.16.
 */
class MyProducer implements Runnable {
    private final static Logger LOGGER = LogManager.getLogger(MyProducer.class);

    private final Channel channel;
    private final String exchangeName;

    private int messageId;

    MyProducer(String exchangeName) throws Exception {
        this.exchangeName = exchangeName;
        this.messageId = 0;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Thread.sleep(1000);
                postMessage();
            }
        } catch (InterruptedException e) {
            LOGGER.info("thread interrupted");
        }
    }

    private void postMessage() {
        LOGGER.info("Generate message: {}", messageId);
        publish(String.valueOf(messageId));
        messageId++;
    }

    private void publish(String message) {
        try {
            channel.basicPublish(exchangeName, "", null, message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
