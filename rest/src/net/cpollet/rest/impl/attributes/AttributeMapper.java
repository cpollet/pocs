package net.cpollet.rest.impl.attributes;

import net.cpollet.rest.api.attributes.CustomAttribute;
import net.cpollet.rest.api.attributes.PredefinedAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class AttributeMapper {
    private Map<String, CustomAttribute> map = new HashMap<>();

    {
        map.put(PredefinedAttribute.FIRST_NAME.toQuery(), new CustomAttribute("persons", "first_name"));
        map.put(PredefinedAttribute.LAST_NAME.toQuery(), new CustomAttribute("persons", "last_name"));
    }
}
