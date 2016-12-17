package net.cpollet.rabbitmq;

/**
 * Created by cpollet on 17.12.16.
 */
public class Configuration {
    private final String exchangeName;
    private final String queueName;
    private final String deadExchangeName;
    private final String deadQueueName;

    public Configuration(String exchangeName, String queueName, String deadExchangeName, String deadQueueName) {
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.deadExchangeName = deadExchangeName;
        this.deadQueueName = deadQueueName;
    }

    public Configuration(String exchangeName) {
        this(exchangeName, null, null, null);
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getDeadExchangeName() {
        return deadExchangeName;
    }

    public String getDeadQueueName() {
        return deadQueueName;
    }
}
