package net.cpollet.pocs.readapi.client;

import net.cpollet.pocs.readapi.api.AttributeService;
import net.cpollet.pocs.readapi.api.Attributes;
import net.cpollet.pocs.readapi.helper.ReflectionTransformer;
import net.cpollet.pocs.readapi.helper.Transformer;
import net.cpollet.pocs.readapi.impl.AttributeServiceImpl;
import net.cpollet.pocs.readapi.impl.attributes.resolver.AttributesResolver;
import net.cpollet.pocs.readapi.impl.attributes.resolver.StaticAttributeResolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class Main {
    private final AttributeService attributeService;

    public Main() {
        AttributesResolver attributeResolver = new StaticAttributeResolver();
        this.attributeService = new AttributeServiceImpl(attributeResolver);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        Map<String, String> result = attributeService.getAttributes("key", Arrays.asList(
                Attributes.FIRST_NAME,
                Attributes.CAT_NAME,
                Attributes.AGE
        ));

        for (Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        displayDTO(result);
    }

    private void displayDTO(Map<String, String> result) {
        Transformer transformer = createTransformer();

        Person person = transformer.transform(result, Person.class);

        System.out.println(person);
    }

    /**
     * This could typically be a Spring injected instance of a transformer
     */
    private Transformer createTransformer() {
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
