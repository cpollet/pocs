package net.cpollet.pocs.oop.client.fibonacci;

import net.cpollet.pocs.oop.values.IntegerNumber;

/**
 * @author Christophe Pollet
 */
class FibonacciNumber extends IntegerNumber {
    private final IntegerNumber second;
    private final IntegerNumber first;

    FibonacciNumber(IntegerNumber first, IntegerNumber second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Integer value() {
        return first.intValue() + second.intValue();
    }
}
