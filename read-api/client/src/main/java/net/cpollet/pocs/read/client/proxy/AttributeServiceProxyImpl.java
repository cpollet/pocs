package net.cpollet.pocs.read.client.proxy;

import net.cpollet.pocs.read.api.AttributeService;
import net.cpollet.pocs.read.client.data.Person;
import net.cpollet.pocs.read.helper.Transformer;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class AttributeServiceProxyImpl implements AttributeServiceProxy {
    private final AttributeService attributeService;
    private final Transformer transformer;

    public AttributeServiceProxyImpl(AttributeService attributeService, Transformer transformer) {
        this.attributeService = attributeService;
        this.transformer = transformer;
    }

    @Override
    public Person getPersonAttributes(String key, List<String> attribute) {
        return transformer.transform(attributeService.getAttributes(key, attribute), Person.class);
    }
}
