package net.cpollet.rabbitmq;

/**
 * Created by cpollet on 09.12.16.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        final String exchangeName = "exchange";

        switch (args[0]) {
            case "producer":
                new MyProducer(consumerConfiguration(exchangeName, null)).run();
                break;
            case "consumer":
                new MyConsumer(consumerConfiguration(exchangeName, args[1])).run();
                break;
        }
    }

    private static Configuration consumerConfiguration(String exchange, String queue) {
        String queueName = exchange + "." + queue;
        return new Configuration(exchange, queueName);
    }
}
