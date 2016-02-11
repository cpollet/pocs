package net.cpollet.pocs.readapi.impl.fetching;

import net.cpollet.pocs.readapi.impl.attributes.Attribute;
import net.cpollet.pocs.readapi.impl.attributes.ColumnAttribute;
import net.cpollet.pocs.readapi.impl.attributes.DynamicAttribute;
import net.cpollet.pocs.readapi.impl.attributes.KeyValueAttribute;

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
