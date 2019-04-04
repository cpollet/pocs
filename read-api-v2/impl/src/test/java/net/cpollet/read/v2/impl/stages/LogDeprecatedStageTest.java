package net.cpollet.read.v2.impl.stages;

import net.cpollet.read.v2.api.attribute.AttributeDef;
import net.cpollet.read.v2.api.execution.Response;
import net.cpollet.read.v2.impl.attribute.DirectAttributeStore;
import net.cpollet.read.v2.impl.execution.InternalRequestHelper;
import net.cpollet.read.v2.impl.execution.InternalResponseHelper;
import net.cpollet.read.v2.impl.testsupport.NoopStage;
import net.cpollet.read.v2.impl.testsupport.StringId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class LogDeprecatedStageTest {
    @Test
    void insertsWarnMessage_whenAnAttributeIsDeprecated() {
        // GIVEN
        LogDeprecatedStage<StringId> stage = new LogDeprecatedStage<>(new NoopStage());
        DirectAttributeStore<StringId> store = new DirectAttributeStore<>(Arrays.asList(
                new AttributeDef<>(
                        "non-deprecated", false, false, null, Collections.emptySet(), null, null
                ),
                new AttributeDef<>(
                        "deprecated", false, true, null, Collections.emptySet(), null, null
                )
        ));

        // WHEN
        Response<StringId> res = InternalResponseHelper.toResponse(
                store,
                stage.execute(
                        InternalRequestHelper.from(store)
                )
        );

        // THEN
        Assertions.assertThat("[deprecated] is deprecated").isIn(res.messages());
        Assertions.assertThat("[non-deprecated] is deprecated").isNotIn(res.messages());
    }
}