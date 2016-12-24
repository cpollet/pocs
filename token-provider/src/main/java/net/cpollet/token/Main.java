package net.cpollet.token;

/**
 * Created by cpollet on 24.12.16.
 */
public class Main {

    public static void main(String[] args) throws KeyNotFoundException, InterruptedException, InvalidTimeoutAliasException, InvalidDataException {
        TokenService tokenService = new RedisTokenService(new SimpleTimeoutProvider());

        String key = tokenService.store("some data", "5SEC");

        String value = tokenService.retrieve(key);

        System.out.println(value);

        System.out.println("I'm taking a 5 sec nap...");
        Thread.sleep(5000);

        tokenService.retrieve(key);
    }

}
