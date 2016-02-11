package net.cpollet.pocs.read.service.fetching;

import net.cpollet.pocs.read.service.attributes.Attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class FetchingStrategyFacade implements FetchingStrategy<Attribute> {
    private Map<Class<? extends Attribute>, FetchingStrategy> fetchingStrategies;

    public FetchingStrategyFacade() {
        this.fetchingStrategies = new HashMap<>();
    }

    /**
     * Registers a FetchingStrategy for a given Attribute type. This can be improved by automatically scanning the fetching
     * strategies and get their listening attributes types from either a method that returns a list or an annotation at class level.
     * This method implements a fluent interface.
     *
     * @param fetchingStrategy
     * @param attributeClass
     * @return this
     */
    public <T extends Attribute> FetchingStrategyFacade register(FetchingStrategy<T> fetchingStrategy, Class<T> attributeClass) {
        fetchingStrategies.put(attributeClass, fetchingStrategy);
        return this;
    }

    @Override
    public void append(Attribute attribute) {
        FetchingStrategy fetchingStrategy = fetchingStrategies.get(attribute.getClass());
        //noinspection unchecked
        fetchingStrategy.append(attribute);
    }

    @Override
    public Map<Attribute, String> fetch() {
        //noinspection unchecked
        return fetchingStrategies.values()
                .stream()                                         // loop through all strategies,
                .map(FetchingStrategy::fetch)                     // fetches attributes from each of them
                .collect(HashMap::new, Map::putAll, Map::putAll); // and combine the results in a final map
    }
}
