package net.cpollet.rest.api.attributes;

/**
 * @author Christophe Pollet
 */
public class PredefinedAttribute implements Attribute {
    public final static Attribute FIRST_NAME = new PredefinedAttribute("FIRST_NAME");
    public final static Attribute LAST_NAME = new PredefinedAttribute("LAST_NAME");

    private final String name;

    public PredefinedAttribute(String name) {
        this.name = name;
    }

    @Override
    public String toQuery() {
        return name;
    }
}
