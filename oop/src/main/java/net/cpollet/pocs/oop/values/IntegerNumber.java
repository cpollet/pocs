package net.cpollet.pocs.oop.values;

import net.cpollet.pocs.oop.values.Value;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Christophe Pollet
 */
public abstract class IntegerNumber extends Number implements Value<Integer> {
    @Override
    public Integer value() {
        return intValue();
    }

    @Override
    public long longValue() {
        throw new NotImplementedException();
    }

    @Override
    public float floatValue() {
        throw new NotImplementedException();
    }

    @Override
    public double doubleValue() {
        throw new NotImplementedException();
    }
}
