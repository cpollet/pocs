package net.cpollet.pocs.read.service.fetching;

import net.cpollet.pocs.read.service.attributes.Attribute;

import java.util.Map;

/**
 * @author Christophe Pollet
 */
public interface FetchingStrategy<T extends Attribute> {
    void append(T attribute);

    Map<T, String> fetch();
}
