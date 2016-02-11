package net.cpollet.pocs.read.helper;

import net.cpollet.pocs.read.api.AttributeService;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class GenericProxyImpl<T> implements GenericProxy<T> {
    private final AttributeService attributeService;
    private final Transformer transformer;
    private final Class<T> targetClass;

    public GenericProxyImpl(AttributeService attributeService, Transformer transformer, Class<T> targetClass) {
        this.attributeService = attributeService;
        this.transformer = transformer;
        this.targetClass = targetClass;
    }

    @Override
    public T getAttributes(String key, List<String> attributes) {
        return transformer.transform(attributeService.getAttributes(key, attributes), targetClass);
    }
}
