package net.cpollet.token;

import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * Created by cpollet on 24.12.16.
 */
public class RedisTokenService implements TokenService {
    private final Jedis jedis;
    private final TimeoutProvider timeoutProvider;

    public RedisTokenService(TimeoutProvider timeoutProvider) {
        this.timeoutProvider = timeoutProvider;
        jedis = new Jedis("localhost");
    }

    @Override
    public String store(String data, Long timeout) throws InvalidDataException {
        if (data == null) {
            throw new InvalidDataException("null is not a valid value to store");
        }

        String key = UUID.randomUUID().toString();

        jedis.psetex(key, timeout, data);

        return key;
    }

    @Override
    public String store(String data, String timeout) throws InvalidTimeoutAliasException, InvalidDataException {
        return store(data, timeoutProvider.getTimeoutFor(timeout));
    }

    @Override
    public String retrieve(String key) throws KeyNotFoundException {
        String value = jedis.get(key);

        if (value == null) {
            throw new KeyNotFoundException(key);
        }

        return value;
    }

    @Override
    public boolean exists(String key) {
        return jedis.get(key) != null;
    }

    @Override
    public void expire(String key) {
        jedis.expire(key, 0);
    }
}
