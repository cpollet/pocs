package net.cpollet.pocs.oop.control.branching;

import net.cpollet.pocs.oop.values.Value;

/**
 * @author Christophe Pollet
 */
@FunctionalInterface
public interface IfAlgorithm<T> extends Value<T> {
    T value();
}
