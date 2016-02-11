package net.cpollet.pocs.readapi.client;

import net.cpollet.pocs.readapi.api.AttributeService;
import net.cpollet.pocs.readapi.impl.AttributeServiceImpl;
import net.cpollet.pocs.readapi.impl.attributes.AttributesResolver;
import net.cpollet.pocs.readapi.impl.attributes.StaticAttributeResolver;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class ReadClient {
    private final AttributeService attributeService;

    public ReadClient() {
        AttributesResolver attributeResolver = new StaticAttributeResolver();
        this.attributeService = new AttributeServiceImpl(attributeResolver);
    }

    public static void main(String[] args) {
        new ReadClient().run();
    }

    public void run() {
        Map<String, String> result = attributeService.getAttributes("key", Arrays.asList("FIRST_NAME", "CAT_NAME", "AGE"));

        for (Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
