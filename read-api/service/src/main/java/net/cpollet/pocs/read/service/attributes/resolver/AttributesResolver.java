package net.cpollet.pocs.read.service.attributes.resolver;

import net.cpollet.pocs.read.service.attributes.Attribute;

/**
 * @author Christophe Pollet
 */
public interface AttributesResolver {
    Attribute resolve(String attributeName);
}
