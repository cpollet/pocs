package net.cpollet.rest.api.restrictions;

import net.cpollet.rest.api.attributes.Attribute;

/**
 * @author Christophe Pollet
 */
public class Restrictions {
    public static Restriction equals(Attribute attribute, String value) {
        return new Equals(attribute, value);
    }

    public static Restriction contains(Attribute attribute, String value) {
        return new Contains(attribute, value);
    }

    public static Restriction and(Restriction left, Restriction right) {
        return new And(left, right);
    }

    public static Restriction or(Restriction left, Restriction right) {
        return new Or(left, right);
    }

    public static Restriction not(Restriction restriction) {
        return new Not(restriction);
    }
}
