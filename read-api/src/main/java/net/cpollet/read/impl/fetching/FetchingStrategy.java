package net.cpollet.read.impl.fetching;

import net.cpollet.read.impl.attributes.Attribute;

import java.util.Map;

/**
 * @author Christophe Pollet
 */
public interface FetchingStrategy<T extends Attribute> {
    void append(T attribute);

    Map<T, String> fetch();
}
