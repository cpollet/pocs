package net.cpollet.pocs.readapi.impl.attributes.resolver;

import net.cpollet.pocs.readapi.impl.attributes.Attribute;

/**
 * @author Christophe Pollet
 */
public interface AttributesResolver {
    Attribute resolve(String attributeName);
}
