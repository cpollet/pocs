package net.cpollet.read.v2.impl.execution;

import net.cpollet.read.v2.api.attribute.AccessLevelPredicate;
import net.cpollet.read.v2.api.attribute.AttributeDef;
import net.cpollet.read.v2.api.attribute.AttributeStore;
import net.cpollet.read.v2.api.domain.Id;
import net.cpollet.read.v2.api.domain.IdValidator;
import net.cpollet.read.v2.api.execution.Executor;
import net.cpollet.read.v2.api.execution.Request;
import net.cpollet.read.v2.api.execution.Response;
import net.cpollet.read.v2.impl.Guarded;
import net.cpollet.read.v2.impl.stages.AttributeConversionStage;
import net.cpollet.read.v2.impl.stages.CreateRequestExecutionStage;
import net.cpollet.read.v2.impl.stages.DeleteRequestExecutionStage;
import net.cpollet.read.v2.impl.stages.ExpandStarStage;
import net.cpollet.read.v2.impl.stages.FilteringStage;
import net.cpollet.read.v2.impl.stages.IdsValidationStage;
import net.cpollet.read.v2.impl.stages.LogDeprecatedStage;
import net.cpollet.read.v2.impl.stages.ModeValidationStage;
import net.cpollet.read.v2.impl.stages.ReadRequestExecutionStage;
import net.cpollet.read.v2.impl.stages.RequestHaltStage;
import net.cpollet.read.v2.impl.stages.SearchRequestExecutionStage;
import net.cpollet.read.v2.impl.stages.Stage;
import net.cpollet.read.v2.impl.stages.TimerStage;
import net.cpollet.read.v2.impl.stages.UpdateRequestExecutionStage;
import net.cpollet.read.v2.impl.stages.ValueConversionStage;

import java.util.function.Function;

public class DefaultExecutor<IdType extends Id> implements Executor<IdType> {
    private final Stage<IdType, String> readStack;
    private final Stage<IdType, String> updateStack;
    private final Stage<IdType, String> deleteStack;
    private final Stage<IdType, String> createStack;
    private final Stage<IdType, String> searchStack;
    private final AttributeStore<IdType> attributeStore;

    public DefaultExecutor(AttributeStore<IdType> attributeStore, IdValidator<IdType> idValidator, AccessLevelPredicate<IdType> filteringPredicate, Configuration configuration) {
        this.attributeStore = attributeStore;
        this.readStack =
                new TimerStage<>(
                        new ExpandStarStage<>(attributeStore,
                                rwStages(configuration, idValidator, filteringPredicate, AttributeDef.Mode.READ,
                                        new ReadRequestExecutionStage<>()
                                )
                        )
                );
        this.updateStack =
                new TimerStage<>(
                        rwStages(configuration, idValidator, filteringPredicate, AttributeDef.Mode.WRITE,
                                new UpdateRequestExecutionStage<>(
                                        new RequestHaltStage<>(haltOnUpdateError(configuration),
                                                new ReadRequestExecutionStage<>()
                                        )
                                )
                        )
                );
        this.deleteStack =
                new TimerStage<>(
                        rwdStages(configuration, idValidator, AttributeDef.Mode.DELETE,
                                new DeleteRequestExecutionStage<>()
                        )
                );
        this.createStack =
                new TimerStage<>(
                        rwdcStages(configuration, AttributeDef.Mode.WRITE,
                                new CreateRequestExecutionStage<>(attributeStore,
                                        new UpdateRequestExecutionStage<>(x -> new InternalResponse<>()),
                                        new RequestHaltStage<>(haltOnUpdateError(configuration),
                                                new ReadRequestExecutionStage<>()
                                        )
                                )
                        )
                );
        this.searchStack =
                new TimerStage<>(
                        rwStages(configuration, idValidator, filteringPredicate, AttributeDef.Mode.SEARCH,
                                new SearchRequestExecutionStage<>()
                        )
                );
    }

    /**
     * Stages used for READ and WRITE requests
     */
    private Stage<IdType, String> rwStages(Configuration configuration, IdValidator<IdType> idValidator, AccessLevelPredicate<IdType> filteringPredicate, AttributeDef.Mode mode, Stage<IdType, AttributeDef<IdType>> inner) {
        return rwdStages(configuration, idValidator, mode,
                new FilteringStage<>(
                        filteringPredicate,
                        new ValueConversionStage<>(AttributeDef::caster,
                                new ValueConversionStage<>(AttributeDef::converter,
                                        new RequestHaltStage<>(haltOnInputValueConversionError(configuration),
                                                inner
                                        )
                                )
                        )
                )
        );
    }

    /**
     * Stages used for READ, WRITE and DELETE requests
     */
    private Stage<IdType, String> rwdStages(Configuration configuration, IdValidator<IdType> idValidator, AttributeDef.Mode mode, Stage<IdType, AttributeDef<IdType>> inner) {
        return rwdcStages(configuration, mode,
                new IdsValidationStage<>(idValidator,
                        new RequestHaltStage<>(haltOnIdValidationError(configuration),
                                inner
                        )
                )
        );
    }

    /**
     * Stages used for READ, WRITE, DELETE and CREATE requests
     */
    private Stage<IdType, String> rwdcStages(Configuration configuration, AttributeDef.Mode mode, Stage<IdType, AttributeDef<IdType>> inner) {
        return new AttributeConversionStage<>(attributeStore,
                new RequestHaltStage<>(haltOnAttributeConversionError(configuration),
                        new ModeValidationStage<>(mode,
                                new RequestHaltStage<>(haltOnModeError(configuration),
                                        new LogDeprecatedStage<>(
                                                inner
                                        )
                                )
                        )
                )
        );
    }

    private Function<Guarded, Boolean> haltOnAttributeConversionError(Configuration configuration) {
        return req -> configuration.haltOnAttributeConversionError && req.hasGuardFlag(Guarded.Flag.ATTRIBUTE_CONVERSION_ERROR);
    }

    private Function<Guarded, Boolean> haltOnModeError(Configuration configuration) {
        return req -> configuration.haltOnModeError && req.hasGuardFlag(Guarded.Flag.INVALID_MODE);
    }

    private Function<Guarded, Boolean> haltOnIdValidationError(Configuration configuration) {
        return req -> configuration.haltOnIdValidationError && req.hasGuardFlag(Guarded.Flag.INVALID_IDS);
    }

    private Function<Guarded, Boolean> haltOnInputValueConversionError(Configuration configuration) {
        return req -> configuration.haltOnInputValueConversionError && req.hasGuardFlag(Guarded.Flag.INPUT_VALUE_CONVERSION_ERROR);
    }

    private Function<Guarded, Boolean> haltOnUpdateError(Configuration configuration) {
        return req -> configuration.haltOnUpdateError && req.hasGuardFlag(Guarded.Flag.UPDATE_ERROR);
    }

    @Override
    public AttributeStore<IdType> attributeStore() {
        return attributeStore;
    }

    @Override
    public Response<IdType> read(Request<IdType> request) {
        return InternalResponse.unwrap(
                readStack.execute(
                        InternalRequest.wrap(InternalRequest.RequestType.READ, request)
                )
        );
    }

    @Override
    public Response<IdType> update(Request<IdType> request) {
        return InternalResponse.unwrap(
                updateStack.execute(
                        InternalRequest.wrap(InternalRequest.RequestType.UPDATE, request)
                )
        );
    }

    @Override
    public Response<IdType> create(Request<IdType> request) {
        return InternalResponse.unwrap(
                createStack.execute(
                        InternalRequest.wrap(InternalRequest.RequestType.CREATE, request)
                )
        );
    }

    @Override
    public Response<IdType> delete(Request<IdType> request) {
        return InternalResponse.unwrap(
                deleteStack.execute(
                        InternalRequest.wrap(InternalRequest.RequestType.DELETE, request)
                )
        );
    }

    @Override
    public Response<IdType> search(Request<IdType> request) {
        return InternalResponse.unwrap(
                searchStack.execute(
                        InternalRequest.wrap(InternalRequest.RequestType.SEARCH, request)
                )
        );
    }

    public static class Configuration {
        private final boolean haltOnAttributeConversionError;
        private final boolean haltOnIdValidationError;
        private final boolean haltOnInputValueConversionError;
        private final boolean haltOnUpdateError;
        private final boolean haltOnModeError;

        public Configuration(boolean haltOnAttributeConversionError, boolean haltOnIdValidationError, boolean haltOnInputValueConversionError, boolean haltOnUpdateError, boolean haltOnModeError) {
            this.haltOnAttributeConversionError = haltOnAttributeConversionError;
            this.haltOnIdValidationError = haltOnIdValidationError;
            this.haltOnInputValueConversionError = haltOnInputValueConversionError;
            this.haltOnUpdateError = haltOnUpdateError;
            this.haltOnModeError = haltOnModeError;
        }
    }
}
