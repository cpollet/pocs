package net.cpollet.pocs.read.service.fetching;

import net.cpollet.pocs.read.service.attributes.Attribute;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Christophe Pollet
 */
public abstract class BaseFetchingStrategy<T extends Attribute> implements FetchingStrategy<T> {
    private final List<T> attributes;

    public BaseFetchingStrategy() {
        this.attributes = new LinkedList<>();
    }

    protected List<T> getAttributesToFetch() {
        return attributes;
    }

    @Override
    public void append(T attribute) {
        attributes.add(attribute);
    }
}
