package net.cpollet.pocs.oop.control.branching;

import net.cpollet.pocs.oop.control.Predicate;
import net.cpollet.pocs.oop.values.Value;

/**
 * @author Christophe Pollet
 */
public class If<T, R> implements Value<R> {
    private final Value<T> value;
    private final Predicate<T> predicate;
    private final Value<R> resultIfTrue;
    private final Value<R> resultIfFalse;

    public If(Value<T> value, Predicate<T> predicate, Value<R> resultIfTrue) {
        this(value, predicate, resultIfTrue, new Value<R>() { // can be replaced with a lambda
            @Override
            public R value() {
                return null;
            }
        });
    }

    public If(Value<T> value, Predicate<T> predicate, Value<R> resultIfTrue, Value<R> resultIfFalse) {
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
