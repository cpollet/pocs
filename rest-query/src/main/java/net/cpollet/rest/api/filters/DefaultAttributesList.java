package net.cpollet.rest.api.filters;

import net.cpollet.rest.api.attributes.Attribute;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Christophe Pollet
 */
public class DefaultAttributesList implements AttributeList {
    private List<Attribute> attributes;

    public DefaultAttributesList() {
        this.attributes = new LinkedList<>();
    }

    @Override
    public AttributeList add(Attribute attribute) {
        attributes.add(attribute);
        return this;
    }

    @Override
    public String toQuery() {
        return String.join(",", attributes.stream()
                .map(Attribute::toQuery)
                .collect(Collectors.toList()));
    }
}
