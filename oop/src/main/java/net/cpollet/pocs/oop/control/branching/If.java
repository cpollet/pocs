package net.cpollet.pocs.oop.control.branching;

import net.cpollet.pocs.oop.control.Predicate;
import net.cpollet.pocs.oop.values.Value;

/**
 * @author Christophe Pollet
 */
public class If<T, R> implements Value<R> {
    private final Value<T> value;
    private final Predicate<T> predicate;
    private final IfAlgorithm<R> resultIfTrue;
    private final IfAlgorithm<R> resultIfFalse;

    public If(Value<T> value, Predicate<T> predicate, IfAlgorithm<R> resultIfTrue) {
        this(value, predicate, resultIfTrue, new IfAlgorithm<R>() {
            @Override
            public R value() {
                return null;
            }
        });
    }

    public If(Value<T> value, Predicate<T> predicate, IfAlgorithm<R> resultIfTrue, IfAlgorithm<R> resultIfFalse) {
        this.value = value;
        this.predicate = predicate;
        this.resultIfTrue = resultIfTrue;
        this.resultIfFalse = resultIfFalse;
    }

    @Override
    public R value() {
        if (predicate.matches(value.value())) {
            return resultIfTrue.value();
        } else {
            return resultIfFalse.value();
        }
    }

    public void evaluate() {
        value();
    }
}
