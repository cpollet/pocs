package net.cpollet.rabbitmq;

/**
 * Created by cpollet on 09.12.16.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        final String exchangeName = "myExchange";

        switch (args[0]) {
            case "producer":
                new MyProducer(new Configuration(exchangeName)).run();
                break;
            case "consumer":
                new MyConsumer(consumerConfiguration(exchangeName, args[1])).run();
                break;
            case "retry":

                new MyRetry(consumerConfiguration(exchangeName, args[1])).run();
        }
    }

    private static Configuration consumerConfiguration(String exchange, String queue) {
        String queueName = exchange + "." + queue;
        String deadExchangeName = exchange + "_dead";
        String deadQueueName = deadExchangeName + "." + queue;
        return new Configuration(exchange, queueName, deadExchangeName, deadQueueName);
    }
}
