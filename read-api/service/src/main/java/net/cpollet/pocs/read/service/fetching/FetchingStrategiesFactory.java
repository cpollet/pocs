package net.cpollet.pocs.read.service.fetching;

import net.cpollet.pocs.read.service.attributes.Attribute;
import net.cpollet.pocs.read.service.attributes.ColumnAttribute;
import net.cpollet.pocs.read.service.attributes.DynamicAttribute;
import net.cpollet.pocs.read.service.attributes.KeyValueAttribute;

/**
 * @author Christophe Pollet
 */
public class FetchingStrategiesFactory {
    public static FetchingStrategy<Attribute> create() {
        FetchingStrategyFacade fetchingStrategy = new FetchingStrategyFacade();

        fetchingStrategy
                .register(new ColumnAttributeFetchingStrategy(), ColumnAttribute.class)
                .register(new DynamicAttributeFetchingStrategy(), DynamicAttribute.class)
                .register(new KeyValueAttributeFetchingStrategy(), KeyValueAttribute.class);

        return fetchingStrategy;
    }
}
