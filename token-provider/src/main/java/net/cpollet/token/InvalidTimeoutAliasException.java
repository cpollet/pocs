package net.cpollet.token;

/**
 * Created by cpollet on 24.12.16.
 */
public class InvalidTimeoutAliasException extends Throwable {
    private final static String INVALID_ALIAS = "Timeout alias %s is not valid";

    public InvalidTimeoutAliasException(String timeout) {
        super(String.format(INVALID_ALIAS, timeout));
    }
}
