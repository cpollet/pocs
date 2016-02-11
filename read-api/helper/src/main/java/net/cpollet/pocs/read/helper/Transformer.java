package net.cpollet.pocs.read.helper;

import java.util.Map;

/**
 * @author Christophe Pollet
 */
public interface Transformer {
    <T> T transform(Map<String, String> map, Class<T> target);
}
