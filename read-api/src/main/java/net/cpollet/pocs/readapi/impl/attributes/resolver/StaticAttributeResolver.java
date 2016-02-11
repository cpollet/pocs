package net.cpollet.pocs.readapi.impl.attributes.resolver;

import net.cpollet.pocs.readapi.impl.attributes.Attribute;
import net.cpollet.pocs.readapi.impl.attributes.ColumnAttribute;
import net.cpollet.pocs.readapi.impl.attributes.DynamicAttribute;
import net.cpollet.pocs.readapi.impl.attributes.KeyValueAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * One possible implementation of an AttributeResolver. The same could be configured from properties files, database, spring
 * configuration or any other way.
 *
 * @author Christophe Pollet
 */
public class StaticAttributeResolver implements AttributesResolver {
    private final static Map<String, Attribute> attributes = new HashMap<String, Attribute>() {{
        put("FIRST_NAME", new ColumnAttribute("FIRST_NAME", "PERSONS", "FIRSTNAME"));
        put("LAST_NAME", new ColumnAttribute("LAST_NAME", "PERSONS", "LASTNAME"));
        put("AGE", new DynamicAttribute("AGE", "getAge"));
        put("CAT_NAME", new KeyValueAttribute("CAT_NAME", 42));
    }};

    @Override
    public Attribute resolve(String attributeName) {
        if (!attributes.containsKey(attributeName)) {
            throw new IllegalArgumentException("Attribute " + attributeName + " is invalid");
        }

        return attributes.get(attributeName);
    }
}
