package net.cpollet.pocs.read.client;

import net.cpollet.pocs.read.api.AttributeService;
import net.cpollet.pocs.read.api.Attributes;
import net.cpollet.pocs.read.client.data.Person;
import net.cpollet.pocs.read.helper.Transformer;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class ServiceConsumer {
    private final AttributeService attributeService;
    private final Transformer transformer;

    public ServiceConsumer(AttributeService attributeService, Transformer transformer) {
        this.attributeService = attributeService;
        this.transformer = transformer;
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
        Person person = transformer.transform(result, Person.class);

        System.out.println(person);
    }
}
