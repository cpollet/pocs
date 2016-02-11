package net.cpollet.pocs.read.client;

import net.cpollet.pocs.read.api.AttributeService;
import net.cpollet.pocs.read.api.Attributes;
import net.cpollet.pocs.read.client.data.Person;
import net.cpollet.pocs.read.client.proxy.ClientProxy;
import net.cpollet.pocs.read.helper.GenericProxy;
import net.cpollet.pocs.read.helper.Transformer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class ServiceConsumer {
    private final AttributeService attributeService;
    private final Transformer transformer;
    private final ClientProxy clientProxy;
    private final GenericProxy genericProxy;

    public ServiceConsumer(AttributeService attributeService,
                           Transformer transformer,
                           ClientProxy clientProxy,
                           GenericProxy genericProxy) {
        this.attributeService = attributeService;
        this.transformer = transformer;
        this.clientProxy = clientProxy;
        this.genericProxy = genericProxy;
    }

    public void run() {
        List<String> attributes = Arrays.asList(
                Attributes.FIRST_NAME,
                Attributes.CAT_NAME,
                Attributes.AGE
        );
        Map<String, String> result = attributeService.getAttributes("key", attributes);

        System.out.println("RAW");
        for (Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("After local transformer");
        displayDTO(result);

        System.out.println("Through custom proxy");
        System.out.println(clientProxy.getAttributes("key", attributes));

        System.out.println("Through generic proxy");
        System.out.println(genericProxy.getAttributes("key", attributes));
    }

    private void displayDTO(Map<String, String> result) {
        Person person = transformer.transform(result, Person.class);

        System.out.println(person);
    }
}
