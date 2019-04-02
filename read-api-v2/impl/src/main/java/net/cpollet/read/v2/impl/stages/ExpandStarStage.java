package net.cpollet.read.v2.impl.stages;

import net.cpollet.read.v2.api.attribute.AttributeStore;
import net.cpollet.read.v2.api.domain.Id;
import net.cpollet.read.v2.api.attribute.AttributeDef;
import net.cpollet.read.v2.impl.execution.InternalRequest;
import net.cpollet.read.v2.impl.execution.InternalResponse;

import java.util.Collections;
import java.util.stream.Collectors;

public class ExpandStarStage<IdType extends Id> implements Stage<IdType, String> {
    private final Stage<IdType, String> next;
    private final AttributeStore<IdType> attributeStore;

    public ExpandStarStage(Stage<IdType, String> next, AttributeStore<IdType> attributeStore) {
        this.next = next;
        this.attributeStore = attributeStore;
    }

    @Override
    public InternalResponse<IdType, String> execute(InternalRequest<IdType, String> request) {
        if (!request.is(InternalRequest.RequestType.READ)) {
            throw new IllegalStateException("Can only apply ExpandStarStage to a request of type READ");
        }

        if (!request.attributes().contains("*")) {
            return next.execute(request);
        }

        return next.execute(
                request
                        .withoutAttributes(Collections.singleton("*"))
                        .withAttributes(
                                attributeStore.directAttributes().stream()
                                        .map(AttributeDef::name)
                                        .collect(Collectors.toSet())
                        )
        );
    }
}
