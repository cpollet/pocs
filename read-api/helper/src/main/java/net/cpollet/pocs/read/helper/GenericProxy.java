package net.cpollet.pocs.read.helper;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public interface GenericProxy<T> {
    T getAttributes(String key, List<String> attributes);
}
