package net.cpollet.pocs.read.service;

import net.cpollet.pocs.read.api.AttributeService;
import net.cpollet.pocs.read.service.attributes.Attribute;
import net.cpollet.pocs.read.service.attributes.resolver.AttributesResolver;
import net.cpollet.pocs.read.service.fetching.FetchingStrategiesFactory;
import net.cpollet.pocs.read.service.fetching.FetchingStrategy;

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
