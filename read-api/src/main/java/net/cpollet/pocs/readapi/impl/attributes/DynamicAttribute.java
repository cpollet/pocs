package net.cpollet.pocs.readapi.impl.attributes;

/**
 * @author Christophe Pollet
 */
public class DynamicAttribute extends BaseAttribute {
    private final String method;

    public DynamicAttribute( String name, String method) {
        super(name);
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
