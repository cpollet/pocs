package net.cpollet.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by cpollet on 17.12.16.
 */
public class MyRetry implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MyRetry.class);

    private final Channel channel;
    private final Configuration configuration;

    public MyRetry(Configuration configuration) throws IOException, TimeoutException {
        this.configuration = configuration;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        channel = connection.createChannel();
        channel.exchangeDeclare(configuration.getExchangeName(), BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare(configuration.getDeadExchangeName(), BuiltinExchangeType.DIRECT);

        channel.queueDeclare(configuration.getDeadQueueName(), false, false, false, null).getQueue();
        channel.queueBind(configuration.getDeadQueueName(), configuration.getDeadExchangeName(), "");
    }

    @Override
    public void run() {
        while (true) {
            // Get the first message in the queue (auto ack = false)
            GetResponse response;
            try {
                response = channel.basicGet(configuration.getDeadQueueName(), false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (response == null) {
                try {
                    LOGGER.info("sleeping for 10 sec");
                    Thread.sleep(10000);
                    continue;
                } catch (InterruptedException e) {
                    return;
                }
            }

            BasicProperties properties = response.getProps();

            try {
                LOGGER.info("republish {}", new String(response.getBody(), "UTF-8"));
                channel.basicPublish(configuration.getExchangeName(), "", (AMQP.BasicProperties) properties, response.getBody());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
