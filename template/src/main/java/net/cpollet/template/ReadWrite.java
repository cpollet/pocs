package net.cpollet.template;

import java.util.List;
import java.util.Map;

/**
 * Created by cpollet on 11.10.16.
 */
public interface ReadWrite<T> {
    Map<T, Map<String, String>> read(List<T> ids, List<String> attributes) throws Exception;

    Map<T, Map<String, String>> write(List<T> ids, Map<String, String> attributeValues) throws Exception;
}
