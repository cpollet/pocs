package net.cpollet.pocs.read.client.context;

import net.cpollet.pocs.read.api.AttributeService;
import net.cpollet.pocs.read.api.Attributes;
import net.cpollet.pocs.read.helper.ReflectionTransformer;
import net.cpollet.pocs.read.helper.Transformer;

import java.util.HashMap;
import java.util.Map;

/**
 * This simulates a bean facotry, such as Spring for instance.
 * @author Christophe Pollet
 */
public class BeanFactory {
    /**
     * Simulates a Spring-like instantiated bean
     * No direct instantiation to keep service as runtime dependency
     */
    public static AttributeService attributeService() throws StartupException {
        try {
            Class<?> resolver = Class.forName("net.cpollet.pocs.read.service.attributes.resolver.StaticAttributeResolver");
            Class<?> resolverInterface = Class.forName("net.cpollet.pocs.read.service.attributes.resolver.AttributesResolver");
            Class<?> service = Class.forName("net.cpollet.pocs.read.service.AttributeServiceImpl");
            return (AttributeService) service.getConstructor(resolverInterface).newInstance(resolver.newInstance());
        }
        catch (Exception e) {
            throw new StartupException(e);
        }
    }

    /**
     * Simulates a Spring-like instantiated bean
     */
    public static Transformer transformer() {
        Map<String, String> map = new HashMap<String, String>() {
            {
                put(Attributes.FIRST_NAME, "firstName");
                put(Attributes.LAST_NAME, "lastName");
                put(Attributes.CAT_NAME, "catName");
                put(Attributes.AGE, "age");
            }
        };

        return new ReflectionTransformer(map);
    }
}
