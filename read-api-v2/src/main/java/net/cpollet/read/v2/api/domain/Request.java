package net.cpollet.read.v2.api.domain;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a request. The instance is immutable and once created, the ids and attributes collections are wrapped
 * in an unmodifiable collection.
 *
 * @param <IdType> the java type of the entity primary key
 */
public class Request<IdType> {
    private final Collection<IdType> ids;
    private final Collection<String> attributes;

    public Request(Collection<IdType> ids, Collection<String> attributes) {
        this.ids = Collections.unmodifiableCollection(ids);
        this.attributes = Collections.unmodifiableCollection(attributes);
    }

    public Collection<IdType> getIds() {
        return ids;
    }

    public Collection<String> getAttributes() {
        return attributes;
    }
}