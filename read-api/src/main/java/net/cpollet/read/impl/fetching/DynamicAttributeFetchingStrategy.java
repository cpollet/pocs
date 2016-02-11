package net.cpollet.read.impl.fetching;

import net.cpollet.read.impl.attributes.DynamicAttribute;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class DynamicAttributeFetchingStrategy extends BaseFetchingStrategy<DynamicAttribute> {
    @Override
    public Map<DynamicAttribute, String> fetch() {
        Person p = new Person();

        Map<DynamicAttribute, String> result = new HashMap<>();

        for (DynamicAttribute attribute : getAttributesToFetch()) {
            try {
                Method method = p.getClass().getMethod(attribute.getMethod());
                result.put(attribute, method.invoke(p).toString());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    private final class Person {
        public Integer getAge() {
            return 42;
        }
    }
}
