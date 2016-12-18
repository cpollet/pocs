package net.cpollet.rabbitmq;

/**
 * Created by cpollet on 17.12.16.
 */
public class Configuration {
    private final String exchangeName;
    private final String queueName;

    public Configuration(String exchangeName, String queueName) {
        this.exchangeName = exchangeName;
        this.queueName = queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getQueueName() {
        return queueName;
    }
}
