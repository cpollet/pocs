package net.cpollet;

import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class TypedMap {
    private final Map<String, String> wrappedMap;

    private TypedMap(Map<String, String> wrappedMap) {
        this.wrappedMap = wrappedMap;
    }

    public static TypedMap of(Map<String, String> wrappedMap) {
        return new TypedMap(wrappedMap);
    }

    public Boolean getBoolean(String key) {
        String value = getString(key);

        if (value == null) {
            return null;
        }

        switch (value.trim().toLowerCase()) {
            case "true":
                return true;
            case "false":
                return false;
            default:
                throw new IllegalArgumentException(key + " cannot be converted to boolean");
        }
    }

    public Long getLong(String key) {
        String value = getString(key);

        if (value == null) {
            return null;
        }

        return Long.parseLong(value);
    }

    public Integer getInteger(String key) {
        String value = getString(key);

        if (value == null) {
            return null;
        }

        return Integer.parseInt(value);
    }

    public Float getFloat(String key) {
        String value = getString(key);

        if (value == null) {
            return null;
        }

        return Float.parseFloat(value);
    }

    public Double geDouble(String key) {
        String value = getString(key);

        if (value == null) {
            return null;
        }

        return Double.parseDouble(value);
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> values) {
        String value = getString(key);

        if (value == null) {
            return null;
        }

        return Enum.valueOf(values, key);
    }

    public String getString(String key) {
        return wrappedMap.get(key);
    }
}
