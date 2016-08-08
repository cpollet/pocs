package net.cpollet.pocs.oop.control.looping;

/**
 * @author Christophe Pollet
 */
@FunctionalInterface
public interface ForAlgorithm<T, I> {
    T result(I index, T initialValue);
}