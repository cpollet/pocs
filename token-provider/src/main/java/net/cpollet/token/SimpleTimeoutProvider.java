package net.cpollet.token;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cpollet on 24.12.16.
 */
public class SimpleTimeoutProvider implements TimeoutProvider {
    private final Map<String, Long> timeouts;

    public SimpleTimeoutProvider() {
        timeouts = new HashMap<>();
        timeouts.put("1SEC", 1000L);
        timeouts.put("2SEC", 2000L);
        timeouts.put("5SEC", 5000L);
        timeouts.put("10SEC", 10000L);
    }

    @Override
    public Long getTimeoutFor(String name) throws InvalidTimeoutAliasException {
        if (!timeouts.containsKey(name)) {
            throw new InvalidTimeoutAliasException("Timeout alias " + name + " is not valid");
        }

        return timeouts.get(name);
    }
}
