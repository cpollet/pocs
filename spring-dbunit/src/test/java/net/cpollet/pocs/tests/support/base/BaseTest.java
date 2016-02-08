package net.cpollet.pocs.tests.support.base;

import org.junit.Rule;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christophe Pollet
 */
public abstract class BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            LOG.info("Starting: " + fullTestName(description));
        }

        @Override
        protected void failed(Throwable e, Description description) {
            LOG.error("Failed: " + fullTestName(description));
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            LOG.warn("Skipped: " + fullTestName(description));
        }

        @Override
        protected void finished(Description description) {
            LOG.info("Success: " + fullTestName(description));
        }

        private String fullTestName(Description description) {
            return description.getClassName() + "#" + description.getMethodName() + "()";
        }
    };
}
