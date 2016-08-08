package net.cpollet.pocs.oop.values;

/**
 * @author Christophe Pollet
 */
public class SimpleIntegerNumber extends IntegerNumber {
    private final int value;

    public SimpleIntegerNumber(int value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return value;
    }
}
