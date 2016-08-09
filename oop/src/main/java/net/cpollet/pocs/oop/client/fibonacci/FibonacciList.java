package net.cpollet.pocs.oop.client.fibonacci;

import net.cpollet.pocs.oop.collections.Enumeration;
import net.cpollet.pocs.oop.collections.IntegerEnumeration;
import net.cpollet.pocs.oop.control.LessThan;
import net.cpollet.pocs.oop.control.branching.If;
import net.cpollet.pocs.oop.control.looping.For;
import net.cpollet.pocs.oop.control.looping.ForAlgorithm;
import net.cpollet.pocs.oop.values.IntegerNumber;
import net.cpollet.pocs.oop.values.SimpleIntegerNumber;
import net.cpollet.pocs.oop.values.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Christophe Pollet
 */
public class FibonacciList implements Value<List<Integer>> {
    private final int first;
    private final int second;
    private final int length;

    public FibonacciList(int length) {
        this(0, 1, length);
    }

    public FibonacciList(int first, int second, int length) {
        new If<>(new SimpleIntegerNumber(length), new LessThan<>(2), new Value<Object>() { // can be replaced with a lambda
            @Override
            public Object value() {
                throw new IllegalArgumentException("length must be at least 20, was " + length);
            }
        }).evaluate();

        this.first = first;
        this.second = second;
        this.length = length;
    }

    @Override
    public List<Integer> value() {
        Enumeration<Integer> enumeration = new IntegerEnumeration(2, length); // first 2 are given
        List<IntegerNumber> initialValue = Arrays.asList(new SimpleIntegerNumber(first), new SimpleIntegerNumber(second));
        ForAlgorithm<List<IntegerNumber>, Integer> algorithm = new ForAlgorithm<List<IntegerNumber>, Integer>() { // can be replaced with a lambda
            @Override
            public List<IntegerNumber> result(Integer index, List<IntegerNumber> initialValue) {
                List<IntegerNumber> result = new ArrayList<>(initialValue);

                IntegerNumber a = initialValue.get(initialValue.size() - 1);
                IntegerNumber b = initialValue.get(initialValue.size() - 2);

                result.add(new FibonacciNumber(b, a));

                return result;
            }
        };

        return new For<>(enumeration, initialValue, algorithm).value()
                .stream()
                .map(Number::intValue)
                .collect(Collectors.toList());
    }
}
