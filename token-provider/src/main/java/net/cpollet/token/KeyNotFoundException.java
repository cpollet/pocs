package net.cpollet.token;

/**
 * Created by cpollet on 24.12.16.
 */
public class KeyNotFoundException extends Exception {
    private final static String MESSAGE = "Key %s not found or expired";

    public KeyNotFoundException(String key) {
        super(String.format(MESSAGE, key));
    }
}
