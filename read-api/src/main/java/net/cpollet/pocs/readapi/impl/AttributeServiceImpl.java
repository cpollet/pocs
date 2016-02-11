package net.cpollet.pocs.readapi.impl;

import net.cpollet.pocs.readapi.api.AttributeService;
import net.cpollet.pocs.readapi.impl.attributes.Attribute;
import net.cpollet.pocs.readapi.impl.attributes.resolver.AttributesResolver;
import net.cpollet.pocs.readapi.impl.fetching.FetchingStrategiesFactory;
import net.cpollet.pocs.readapi.impl.fetching.FetchingStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Christophe Pollet
 */
public class AttributeServiceImpl implements AttributeService {
    private final AttributesResolver attributesResolver;

    public AttributeServiceImpl(AttributesResolver attributesResolver) {
        this.attributesResolver = attributesResolver;
    }

    @Override
    public Map<String, String> getAttributes(String key, List<String> attributeNames) {
        FetchingStrategy<Attribute> fetchingStrategy = FetchingStrategiesFactory.create();

        for (String attributeName : attributeNames) {
            Attribute attribute = attributesResolver.resolve(attributeName);

            fetchingStrategy.append(attribute);
        }

        Map<Attribute, String> attributeValues = fetchingStrategy.fetch();

        return attributeValues.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue));
    }

}
