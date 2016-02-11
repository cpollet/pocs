package net.cpollet.pocs.read.client.context;

import net.cpollet.pocs.read.api.AttributeService;
import net.cpollet.pocs.read.api.Attributes;
import net.cpollet.pocs.read.client.data.Person;
import net.cpollet.pocs.read.client.proxy.ClientProxy;
import net.cpollet.pocs.read.client.proxy.ClientProxyImpl;
import net.cpollet.pocs.read.helper.GenericProxy;
import net.cpollet.pocs.read.helper.GenericProxyImpl;
import net.cpollet.pocs.read.helper.ReflectionTransformer;
import net.cpollet.pocs.read.helper.Transformer;

import java.util.HashMap;
import java.util.Map;

/**
 * This simulates a bean factory, such as Spring for instance.
 *
 * @author Christophe Pollet
 */
public class BeanFactory {
    private static AttributeService attributeService;
    private static Transformer transformer;
    private static ClientProxy clientProxy;
    private static GenericProxy genericProxy;

    /**
     * Simulates a Spring-like instantiated singleton bean
     * No direct instantiation to keep service as runtime dependency
     */
    public static AttributeService attributeService() throws StartupException {
        if (attributeService == null) {
            try {
                Class<?> resolver = Class.forName("net.cpollet.pocs.read.service.attributes.resolver.StaticAttributeResolver");
                Class<?> resolverInterface = Class.forName("net.cpollet.pocs.read.service.attributes.resolver.AttributesResolver");
                Class<?> service = Class.forName("net.cpollet.pocs.read.service.AttributeServiceImpl");
                attributeService = (AttributeService) service.getConstructor(resolverInterface).newInstance(resolver.newInstance());
            }
            catch (Exception e) {
                throw new StartupException(e);
            }
        }

        return attributeService;
    }

    /**
     * Simulates a Spring-like instantiated bean
     */
    public static Transformer transformer() {
        if (transformer == null) {
            Map<String, String> map = new HashMap<String, String>() {
                {
                    put(Attributes.FIRST_NAME, "firstName");
                    put(Attributes.LAST_NAME, "lastName");
                    put(Attributes.CAT_NAME, "catName");
                    put(Attributes.AGE, "age");
                }
            };

            transformer = new ReflectionTransformer(map);
        }

        return transformer;
    }

    /**
     * Simulates a Spring-like instantiated singleton bean
     */
    public static ClientProxy clientProxy() {
        if (clientProxy == null) {
            clientProxy = new ClientProxyImpl(attributeService(), transformer());
        }

        return clientProxy;
    }

    /**
     * Simulates a Spring-like instantiated singleton bean
     */
    public static GenericProxy genericProxy() {
        if (genericProxy == null) {
            genericProxy = new GenericProxyImpl<>(attributeService(), transformer(), Person.class);
        }

        return genericProxy;
    }
}
