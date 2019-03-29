package net.cpollet.read.v2.impl.stages;

import net.cpollet.read.v2.api.domain.Id;
import net.cpollet.read.v2.impl.AttributeDef;
import net.cpollet.read.v2.impl.AttributesGrouper;
import net.cpollet.read.v2.impl.InternalRequest;
import net.cpollet.read.v2.impl.InternalResponse;

import java.util.ArrayList;
import java.util.List;

public class UpdateRequestExecutionStage<IdType extends Id> implements Stage<IdType, AttributeDef<IdType>> {
    private final Stage<IdType, AttributeDef<IdType>> readStage;

    public UpdateRequestExecutionStage(Stage<IdType, AttributeDef<IdType>> readStage) {
        this.readStage = readStage;
    }

    @Override
    public InternalResponse<IdType, AttributeDef<IdType>> execute(InternalRequest<IdType, AttributeDef<IdType>> request) {
        List<String> errors = new ArrayList<>();

        request.attributes(new AttributesGrouper<>()).forEach(
                (method, attributes) -> errors.addAll(
                        method.update(
                                request.values(attributes),
                                request.ids()
                        )
                )
        );

        return readStage.execute(request)
                .withErrors(errors);
    }
}