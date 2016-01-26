package net.cpollet.rest.api.restrictions;

import net.cpollet.rest.api.attributes.Attribute;

/**
 * @author Christophe Pollet
 */
public class Equals extends BaseRestriction {
    private final Attribute attribute;
    private final String value;

    public Equals(Attribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public String toQuery() {
        return "eq(" + attribute.toQuery() + "," + value + ")";
    }
}
