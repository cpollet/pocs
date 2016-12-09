package net.cpollet.rabbitmq;

/**
 * Created by cpollet on 09.12.16.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        switch (args[0]){
            case "producer":
                new Producer("broadcast").run();
                break;
            case "consumer":
                new Consumer("broadcast", args[1]).run();
                break;
        }
    }
}
