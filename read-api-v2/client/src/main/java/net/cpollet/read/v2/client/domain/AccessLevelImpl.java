package net.cpollet.read.v2.client.domain;

import net.cpollet.read.v2.api.attribute.AccessLevel;

public class AccessLevelImpl implements AccessLevel {
    public static final AccessLevel PUBLIC = new AccessLevelImpl("PUBLIC");
    public static final AccessLevel PRIVATE = new AccessLevelImpl("PRIVATE");

    private final String level;

    private AccessLevelImpl(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return level;
    }
}
