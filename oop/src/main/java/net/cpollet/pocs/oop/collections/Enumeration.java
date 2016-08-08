package net.cpollet.pocs.oop.collections;

/**
 * @author Christophe Pollet
 */
public interface Enumeration<T> {
    T current();

    boolean empty();

    Enumeration<T> withoutCurrent();
}
