package net.cpollet.pocs.oop.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christophe Pollet
 */
public class ListEnumeration<T> implements Enumeration<T> {
    private final List<T> list;

    public ListEnumeration(List<T> list) {
        this.list = list;
    }

    @Override
    public T current() {
        return list.get(0);
    }

    @Override
    public boolean empty() {
        return list.isEmpty();
    }

    @Override
    public Enumeration<T> withoutCurrent() {
        ArrayList<T> result = new ArrayList<>(list);
        result.remove(0);

        return new ListEnumeration<>(result);
    }
}
