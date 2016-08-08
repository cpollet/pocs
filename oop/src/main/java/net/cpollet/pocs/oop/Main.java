package net.cpollet.pocs.oop;

import net.cpollet.pocs.oop.collections.EvenIntegerList;
import net.cpollet.pocs.oop.client.fibonacci.FibonacciList;

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
    }
}
