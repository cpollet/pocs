package net.cpollet.pocs.oop.collections;

import net.cpollet.pocs.oop.control.Predicate;
import net.cpollet.pocs.oop.values.Value;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class EvenIntegerList extends FilteredList<Integer> {
    public EvenIntegerList(Value<List<Integer>> list) {
        super(list, new Predicate<Integer>() { // can be replaced by a lambda
            @Override
            public boolean matches(Integer e) {
                return e % 2 == 0;
            }
        });
    }
}
