package net.cpollet.rest.api.filters;

import net.cpollet.rest.api.attributes.Attribute;

/**
 * @author Christophe Pollet
 */
public interface AttributeList {
    AttributeList add(Attribute attribute);

    String toQuery();
}
