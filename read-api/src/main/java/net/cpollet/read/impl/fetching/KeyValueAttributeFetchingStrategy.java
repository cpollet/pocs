package net.cpollet.read.impl.fetching;

import net.cpollet.read.impl.attributes.KeyValueAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class KeyValueAttributeFetchingStrategy extends BaseFetchingStrategy<KeyValueAttribute> {
    private static final Map<Integer, String> localTable = new HashMap<Integer, String>() {{
        put(42, "Le chat");
    }};

    @Override
    public Map<KeyValueAttribute, String> fetch() {
        Map<KeyValueAttribute, String> result = new HashMap<>();

        for (KeyValueAttribute attribute : getAttributesToFetch()) {
            result.put(attribute, localTable.get(attribute.getKey()));
        }

        return result;
    }
}
