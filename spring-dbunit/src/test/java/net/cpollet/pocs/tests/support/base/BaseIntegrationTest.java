package net.cpollet.pocs.tests.support.base;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Christophe Pollet
 */
public abstract class BaseIntegrationTest extends BaseTest {
    public static final String CONFIG_PATH = "config.path";
    private static final Logger LOG = LoggerFactory.getLogger(BaseIntegrationTest.class);

    @BeforeClass
    public static void configureTests() throws IOException {
        String configPath = System.getProperty(CONFIG_PATH);

        if (configPath == null || "".equals(configPath.trim())) {
            System.setProperty(CONFIG_PATH, guessConfigPath());
        }
    }

    private static String guessConfigPath() throws IOException {
        LOG.info("No system property {} found, guessing value", CONFIG_PATH);
        return new File(new File(".").getAbsolutePath() + "/src/test/resources/config").getCanonicalPath();
    }
}
