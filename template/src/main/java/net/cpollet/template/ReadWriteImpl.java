package net.cpollet.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;

/**
 * Created by cpollet on 11.10.16.
 */
public class ReadWriteImpl<T> implements ReadWrite<T> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ReadWriteImpl.class);

    @Override
    public Map<T, Map<String, String>> read(List<T> ids, List<String> attributeNames) throws Exception {
        return access(
                ids,
                attributeNames,
                Function.identity(),
                Collections.emptyList(),
                new Read()
        );
    }

    @Override
    public Map<T, Map<String, String>> read2(List<T> ids, List<String> attributeNames) throws Exception {
        return access(ids, attributeNames, new WorkUnit<List<String>, T>() {
            @Override
            public Collection<String> attributeNames(List<String> attributes) {
                return attributes;
            }

            @Override
            public Object additionalChecks(Object object) {
                return null;
            }

            @Override
            public Map<T, Map<String, String>> business(List<T> ids, List<String> attributes) {
                return null;
            }
        });
    }

    @Override
    public Map<T, Map<String, String>> write(List<T> ids, Map<String, String> attributeValues) throws Exception {
        return access(
                ids,
                attributeValues,
                Map::keySet,
                Collections.singletonList(new AttributeValidator()),
                new Write()
        );
    }

    private <A> Map<T, Map<String, String>> access(List<T> ids,
                                                   A attributes,
                                                   WorkUnit<A, T> work) {
        return work.business(ids, attributes);
    }

    private <A> Map<T, Map<String, String>> access(List<T> ids,
                                                   A attributes,
                                                   Function<A, Collection<String>> attributeNameProvider,
                                                   List<Function<Object, Object>> additionalChecks, // Context, Exception
                                                   Function<Object, Map<T, Map<String, String>>> business) // ??
            throws Exception {
        validateIdsSize(ids);

        List<Exception> exceptions = new ArrayList<>();

        for (String attributeName : attributeNameProvider.apply(attributes)) {
            if (!attributeExists(attributeName)) {
                exceptions.add(new Exception("Attribute does not exist"));
                LOGGER.warn("Attribute " + attributeName + " does not exist");
                continue;
            }

            Object attribute = getAttribute(attributeName);

            if (!hasReadAccess(attribute)) {
                exceptions.add(new Exception("Attribute not accessible"));
                LOGGER.warn("Attribute " + attributeName + " not accessible");
                continue;
            }

            for (Function<Object, Object> additionalCheck : additionalChecks) {
                Object result = additionalCheck.apply("");

                if (result.equals("blababla")) {
                    exceptions.add(new Exception("Attribute not accessible"));
                    LOGGER.warn("Attribute " + attributeName + " not accessible");
                    break;
                }
            }
        }

        if (exceptions.size() > 0) {
            throw new Exception(exceptions.toString());
        }

        Object o = new Object();

        return business.apply(o);
    }

    private boolean hasReadAccess(Object attribute) {
        return attribute.hashCode() > 100;
    }

    private Object getAttribute(String attributeName) {
        return new Object();
    }

    private boolean attributeExists(String attribute) {
        return attribute.startsWith("attribute");
    }

    private void validateIdsSize(List<T> ids) {
        if (ids.size() > 10) {
            throw new IllegalArgumentException("Too many IDs: " + ids.size());
        }
    }

    private interface WorkUnit<A, T> {
        Collection<String> attributeNames(A attributes);

        Object additionalChecks(Object object);

        Map<T, Map<String, String>> business(List<T> ids, A attributes);
    }

    private class Read implements Function<Object, Map<T, Map<String, String>>> {
        @Override
        public Map<T, Map<String, String>> apply(Object o) {
            return Collections.emptyMap();
        }
    }

    private class Write implements Function<Object, Map<T, Map<String, String>>> {
        @Override
        public Map<T, Map<String, String>> apply(Object o) {
            return Collections.emptyMap();
        }
    }

    private final class AttributeValidator implements Function<Object, Object> {
        @Override
        public Boolean apply(Object o) {
            return o.toString().equals("42");
        }
    }

}
