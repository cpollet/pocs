package net.cpollet.pocs.readapi.impl.fetching;

import net.cpollet.pocs.readapi.impl.attributes.Attribute;

import java.util.Map;

/**
 * @author Christophe Pollet
 */
public interface FetchingStrategy<T extends Attribute> {
    void append(T attribute);

    Map<T, String> fetch();
}
