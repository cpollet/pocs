package net.cpollet.read.v2.client.methods;

import net.cpollet.read.v2.api.attribute.AttributeDef;
import net.cpollet.read.v2.api.methods.FetchResult;
import net.cpollet.read.v2.api.methods.Method;
import net.cpollet.read.v2.client.domain.PersonId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressIdMethod implements Method<PersonId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressIdMethod.class);

    @Override
    public FetchResult<PersonId> fetch(List<AttributeDef<PersonId>> attributes, Collection<PersonId> ids) {
        return new FetchResult<>(
                ids.stream()
                        .collect(Collectors.toMap(
                                id -> id,
                                id -> attributes.stream()
                                        .collect(Collectors.toMap(
                                                a -> a,
                                                a -> id.get()
                                        ))
                                )

                        )
        );
    }

    @Override
    public Collection<String> update(Map<AttributeDef<PersonId>, Object> attributeValues, Collection<PersonId> ids) {
        ids.forEach(
                id -> attributeValues.forEach((a, v) -> LOGGER.info("{}:{} -> {}", id, a, v))
        );

        return Collections.emptyList();
    }
}