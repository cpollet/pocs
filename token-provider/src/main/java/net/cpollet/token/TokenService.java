package net.cpollet.token;

/**
 * Created by cpollet on 24.12.16.
 */
public interface TokenService {
    String store(String data, Long timeout) throws InvalidDataException;

    String store(String data, String timeout) throws InvalidTimeoutAliasException, InvalidDataException;

    String retrieve(String key) throws KeyNotFoundException;

    boolean exists(String key);

    void expire(String key);
}
