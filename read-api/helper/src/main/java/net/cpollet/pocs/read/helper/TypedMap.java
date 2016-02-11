package net.cpollet.pocs.read.helper;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Christophe Pollet
 */
public class TypedMap implements Map<String, String> {
    private final Map<String, String> wrappedMap;

    private TypedMap(Map<String, String> wrappedMap) {
        this.wrappedMap = wrappedMap;
    }

    @SuppressWarnings("unchecked")
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

        return Enum.valueOf(values, getString(key));
    }

    public Date getDate(String key) {
        // TODO parse date

        return null; // FIXME return date
    }

    public String getString(String key) {
        return wrappedMap.get(key);
    }

    @Override
    public int size() {
        return wrappedMap.size();
    }

    @Override
    public boolean isEmpty() {
        return wrappedMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return wrappedMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return wrappedMap.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return wrappedMap.get(key);
    }

    @Override
    public String put(String key, String value) {
        return wrappedMap.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return wrappedMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        wrappedMap.putAll(m);
    }

    @Override
    public void clear() {
        wrappedMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return wrappedMap.keySet();
    }

    @Override
    public Collection<String> values() {
        return wrappedMap.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return wrappedMap.entrySet();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return wrappedMap.equals(o);
    }

    @Override
    public int hashCode() {
        return wrappedMap.hashCode();
    }

    @Override
    public String getOrDefault(Object key, String defaultValue) {
        return wrappedMap.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super String> action) {
        wrappedMap.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super String, ? extends String> function) {
        wrappedMap.replaceAll(function);
    }

    @Override
    public String putIfAbsent(String key, String value) {
        return wrappedMap.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return wrappedMap.remove(key, value);
    }

    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        return wrappedMap.replace(key, oldValue, newValue);
    }

    @Override
    public String replace(String key, String value) {
        return wrappedMap.replace(key, value);
    }

    @Override
    public String computeIfAbsent(String key, Function<? super String, ? extends String> mappingFunction) {
        return wrappedMap.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public String computeIfPresent(String key, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return wrappedMap.computeIfPresent(key, remappingFunction);
    }

    @Override
    public String compute(String key, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return wrappedMap.compute(key, remappingFunction);
    }

    @Override
    public String merge(String key, String value, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return wrappedMap.merge(key, value, remappingFunction);
    }
}