package net.cpollet.token;

/**
 * Created by cpollet on 24.12.16.
 */
public interface TimeoutProvider {
    Long getTimeoutFor(String name) throws InvalidTimeoutAliasException;
}
