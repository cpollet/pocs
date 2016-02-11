package net.cpollet.pocs.read.client.proxy;

import net.cpollet.pocs.read.client.data.Person;

import java.util.List;

/**
 * Clients might want to implement such proxy, to hide the complexity of transforming the returned map to a proper DTO
 * using an instance of {@link net.cpollet.pocs.read.helper.Transformer}.
 * @author Christophe Pollet
 */
public interface AttributeServiceProxy {
    Person getPersonAttributes(String key, List<String> attribute);
}
