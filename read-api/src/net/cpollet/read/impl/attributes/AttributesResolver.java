package net.cpollet.read.impl.attributes;

/**
 * @author Christophe Pollet
 */
public interface AttributesResolver {
    Attribute resolve(String attributeName);
}
