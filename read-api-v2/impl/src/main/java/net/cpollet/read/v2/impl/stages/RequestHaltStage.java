package net.cpollet.read.v2.impl.stages;

import net.cpollet.read.v2.api.domain.Id;
import net.cpollet.read.v2.impl.Guarded;
import net.cpollet.read.v2.impl.execution.InternalRequest;
import net.cpollet.read.v2.impl.execution.InternalResponse;

import java.util.Collections;
import java.util.function.Function;

public class RequestHaltStage<IdType extends Id, AttributeType> implements Stage<IdType, AttributeType> {
    private final Stage<IdType, AttributeType> next;
    private final Function<Guarded, Boolean> guard;

    public RequestHaltStage(Function<Guarded, Boolean> guard, Stage<IdType, AttributeType> next) {
        this.next = next;
        this.guard = guard;
    }

    @Override
    public InternalResponse<IdType, AttributeType> execute(InternalRequest<IdType, AttributeType> request) {
        if (guard.apply(request)) {
            return new InternalResponse<>(Collections.emptyMap());
        }

        return next.execute(request);
    }
}