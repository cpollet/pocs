package net.cpollet.pocs.oop.control.looping;


import net.cpollet.pocs.oop.collections.Enumeration;
import net.cpollet.pocs.oop.values.Value;

/**
 * @author Christophe Pollet
 */
public class For<T, I> implements Value<T> {
    private final T initialValue;
    private final Enumeration<I> enumeration;
    private final ForAlgorithm<T, I> algorithm;

    public For(Enumeration<I> enumeration, T initialValue, ForAlgorithm<T, I> algorithm) {
        this.initialValue = initialValue;
        this.enumeration = enumeration;
        this.algorithm = algorithm;
    }

    @Override
    public T value() {
        if (enumeration.empty()) {
            return initialValue;
        }

        T currentValue = algorithm.result(enumeration.current(), initialValue);

        return new For<>(enumeration.withoutCurrent(), currentValue, algorithm).value();
    }
}

