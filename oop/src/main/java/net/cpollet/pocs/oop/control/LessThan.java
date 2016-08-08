package net.cpollet.pocs.oop.control;

/**
 * @author Christophe Pollet
 */
public class LessThan<T extends Number & Comparable<T>> implements Predicate<T> {
    private final T limit;

    public LessThan(T limit) {
        this.limit = limit;
    }

    @Override
    public boolean matches(T e) {
        return e.compareTo(limit) < 0;
    }
}
