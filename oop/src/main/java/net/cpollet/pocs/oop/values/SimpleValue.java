package net.cpollet.pocs.oop.values;

/**
 * @author Christophe Pollet
 */
public class SimpleValue<T> implements Value<T> {
    private final T value;

    public SimpleValue(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }
}
