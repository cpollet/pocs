package net.cpollet.pocs.read.service.attributes;

/**
 * @author Christophe Pollet
 */
public class KeyValueAttribute extends BaseAttribute {
    private final int key;

    public KeyValueAttribute(String name, int key) {
        super(name);
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
