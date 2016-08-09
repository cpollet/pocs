package net.cpollet.pocs.oop;

import net.cpollet.pocs.oop.client.fibonacci.FibonacciList;
import net.cpollet.pocs.oop.collections.EvenIntegerList;
import net.cpollet.pocs.oop.control.branching.If;
import net.cpollet.pocs.oop.values.SimpleIntegerNumber;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class Main {
    public static void main(String[] args) {
        FibonacciList fibonacciNumbers = new FibonacciList(10);
        EvenIntegerList evenFibonacciNumbers = new EvenIntegerList(fibonacciNumbers);

        List<Integer> value = evenFibonacciNumbers.value();

        for (int i = 0; i < value.size(); i++) {
            System.out.println("fibonacci(" + i + ") -> " + value.get(i));
        }

        SimpleIntegerNumber flag = new SimpleIntegerNumber(2);

        System.out.println(
                new If<>(
                        flag,
                        e -> e == 0,
                        new SimpleIntegerNumber(100),
                        new If<>(
                                flag,
                                e -> e == 1,
                                new SimpleIntegerNumber(101),
                                new SimpleIntegerNumber(102)
                        )
                ).value()
        );
    }
}
