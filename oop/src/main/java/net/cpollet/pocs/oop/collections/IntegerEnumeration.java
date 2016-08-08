package net.cpollet.pocs.oop.collections;

/**
 * @author Christophe Pollet
 */
public class IntegerEnumeration implements Enumeration<Integer> {
    private final int from;
    private final int to;
    private final int step;

    public IntegerEnumeration(int from, int to) {
        this(from, to, 1);
    }

    public IntegerEnumeration(int from, int to, int step) {
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public Integer current() {
        return from;
    }

    @Override
    public boolean empty() {
        return from + step > to;
    }

    @Override
    public Enumeration<Integer> withoutCurrent() {
        return new IntegerEnumeration(from + 1, to, step);
    }
}
