package net.cpollet.pocs.tests;

import net.cpollet.pocs.tests.support.base.BaseIntegrationTest;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Christophe Pollet
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/init-test-context.xml")
public class TestIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private SystemPropertyConsumer systemPropertyConsumer;

    @BeforeClass
    public static void configure() {
        System.setProperty("alreadyDefined", "0");
    }

    @Test
    public void systemPropertyDefined() {
        // GIVEN
        // in superclass

        // WHEN
        String configPath = System.getProperty("config.path");

        // THEN
        Assert.assertThat(configPath, CoreMatchers.equalTo("/Users/cpollet/Development/pocs/spring-dbunit/src/test/resources/config"));
    }

    @Test
    public void systemPropertiesAreDefined() {
        // GIVEN
        // in spring context

        // WHEN
        String value = System.getProperty("defined");

        // THEN
        Assert.assertThat(value, CoreMatchers.equalTo("true"));
    }

    @Test
    public void systemPropertyConsumerConfigured() {
        // GIVEN
        // in spring context

        // WHEN
        String deinfed = systemPropertyConsumer.getDefined();

        // THEN
        Assert.assertThat(deinfed, CoreMatchers.equalTo("true"));
    }
}
