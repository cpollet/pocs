package net.cpollet.pocs.read.service.attributes;

/**
 * @author Christophe Pollet
 */
public abstract class BaseAttribute implements Attribute {
    private final String name;

    public BaseAttribute(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
