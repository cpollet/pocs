package net.cpollet.pocs.read.helper;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Christophe Pollet
 */
public class ReflectionTransformer implements Transformer {
    private final Map<String, String> mapKeysToAttributeNames;

    private final Map<Class, BiFunction<TypedMap, String, Object>> casters = new HashMap<Class, BiFunction<TypedMap, String, Object>>() {{
        put(Integer.class, new BiFunction<TypedMap, String, Object>() {
            @Override
            public Object apply(TypedMap typedMap, String key) {
                return typedMap.getInteger(key);
            }
        });
        put(Long.class, (TypedMap typedMap, String key) -> {
            return typedMap.getLong(key);
        });
        put(Boolean.class, (TypedMap typedMap, String key) -> typedMap.getBoolean(key));
        put(Date.class, (typedMap, key) -> typedMap.getDate(key));
        put(String.class, TypedMap::getString);
    }};

    public ReflectionTransformer(Map<String, String> mapKeysToAttributeNames) {
        this.mapKeysToAttributeNames = mapKeysToAttributeNames;
    }

    @Override
    public <T> T transform(Map<String, String> map, Class<T> target) {
        try {
            return doTransform(map, target);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            throw new TransformationException(e);
        }
    }

    private <T> T doTransform(Map<String, String> map, Class<T> target) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        T result = target.newInstance();

        TypedMap typedMap = TypedMap.of(map);

        for (Map.Entry<String, String> attribute : typedMap.entrySet()) {
            Field field = getField(target, attribute);
            Class targetType = field.getType();

            BiFunction<TypedMap, String, Object> caster = casters.get(targetType);
            Object value;

            if (caster != null) {
                value = caster.apply(typedMap, attribute.getKey());
            } else if (field.getType().isEnum()) {
                value = typedMap.getEnum(attribute.getKey(), targetType);
            } else {
                throw new TransformationException("Unable to convert attribute " + attribute.getKey() + " to " + field.getName() + "'s type: " + targetType);
            }

            setFieldValue(result, field, value);
        }

        return result;
    }

    private <T> Field getField(Class<T> target, Map.Entry<String, String> attribute) throws NoSuchFieldException {
        String fieldName = convertToFieldName(attribute.getKey());

        return target.getDeclaredField(fieldName);
    }

    private String convertToFieldName(String mapKey) {
        if (!mapKeysToAttributeNames.containsKey(mapKey)) {
            throw new TransformationException("Unable to map attribute with key " + mapKey);
        }

        return mapKeysToAttributeNames.get(mapKey);
    }

    private void setFieldValue(Object result, Field field, Object value) throws IllegalAccessException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(result, value);
        field.setAccessible(accessible);
    }
}
