package net.cpollet.rabbitmq;

/**
 * Created by cpollet on 09.12.16.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final String exchangeName = "myExchange";

        switch (args[0]) {
            case "producer":
                new MyProducer(exchangeName).run();
                break;
            case "consumer":
                new MyConsumer(exchangeName, args[1]).run();
                break;
        }
    }
}
