package net.cpollet.pocs.tests.support.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Map;
import java.util.Properties;

/**
 * @author Christophe Pollet
 */
public class SystemPropertiesInitializer implements InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(SystemPropertiesInitializer.class);

    private final Properties properties;

    public SystemPropertiesInitializer(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Map.Entry<Object, Object> property : properties.entrySet()) {
            String key = property.getKey().toString();
            String value = property.getValue().toString();

            if (System.getProperties().containsKey(key)) {
                LOG.warn("Exporting system property {}={} (was {})", new String[]{key, value, System.getProperties().getProperty(key)});
            } else {
                LOG.info("Exporting system property {}={}", key, value);
            }

            System.setProperty(key, value);
        }
    }
}
