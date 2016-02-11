package net.cpollet.pocs.readapi.impl.attributes;

/**
 * @author Christophe Pollet
 */
public interface AttributesResolver {
    Attribute resolve(String attributeName);
}
