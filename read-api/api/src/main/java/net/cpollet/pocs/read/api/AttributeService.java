package net.cpollet.pocs.read.api;

import java.util.List;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public interface AttributeService {
    Map<String, String> getAttributes(String key, List<String> attributes);
}
