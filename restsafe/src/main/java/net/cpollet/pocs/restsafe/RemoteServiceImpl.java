package net.cpollet.pocs.restsafe;

import java.util.Iterator;

public class RemoteServiceImpl implements RemoteService, Iterable<Integer> {

    /**
     * Converts a string representing an integer value to an integer
     *
     * @param someString the string to parse
     * @return the integer value represented by the string
     * @throws NumberFormatException when the string does not represent an integer value
     */
    @Override
    public Integer toInteger(String someString) {
        return Integer.parseInt(someString);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return current++;
            }

            @Override
            public void remove() {
                // nop
            }
        };
    }
}
