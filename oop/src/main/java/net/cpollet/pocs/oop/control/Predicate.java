package net.cpollet.pocs.oop.control;

/**
 * @author Christophe Pollet
 */
@FunctionalInterface
public interface Predicate<T> {
    boolean matches(T e);
}
