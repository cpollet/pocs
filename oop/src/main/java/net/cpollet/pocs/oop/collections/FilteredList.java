package net.cpollet.pocs.oop.collections;

import net.cpollet.pocs.oop.control.Predicate;
import net.cpollet.pocs.oop.control.branching.If;
import net.cpollet.pocs.oop.control.looping.For;
import net.cpollet.pocs.oop.control.looping.ForAlgorithm;
import net.cpollet.pocs.oop.values.SimpleValue;
import net.cpollet.pocs.oop.values.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Christophe Pollet
 */
public class FilteredList<T> implements Value<List<T>> {
    private final Value<List<T>> list;
    private final Predicate<T> predicate;

    public FilteredList(Value<List<T>> list, Predicate<T> predicate) {
        this.list = list;
        this.predicate = predicate;
    }

    @Override
    public List<T> value() {
        List<T> initialValue = Collections.emptyList();
        ListEnumeration<T> enumeration = new ListEnumeration<>(list.value());
        ForAlgorithm<List<T>, T> algorithm = new ForAlgorithm<List<T>, T>() { // can be replaced by a lambda
            @Override
            public List<T> result(T index, List<T> initialValue) {

                Predicate<T> doesNotMatch = new Predicate<T>() { // can be replaced by a lambda
                    @Override
                    public boolean matches(T e) {
                        return !predicate.matches(e);
                    }
                };

                Value<List<T>> valueIfTrue = new Value<List<T>>() {
                    @Override
                    public List<T> value() {
                        return initialValue;
                    }
                };

                Value<List<T>> valueIfFalse = new Value<List<T>>() {
                    @Override
                    public List<T> value() {
                        List<T> result = new ArrayList<>(initialValue);
                        result.add(index);
                        return result;
                    }
                };

                return new If<>(new SimpleValue<>(index), doesNotMatch, valueIfTrue, valueIfFalse).value();
            }
        };

        return new For<>(enumeration, initialValue, algorithm).value();
    }
}
