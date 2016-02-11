package net.cpollet.pocs.read.client;

import net.cpollet.pocs.read.api.AttributeService;
import net.cpollet.pocs.read.client.context.BeanFactory;
import net.cpollet.pocs.read.helper.Transformer;

/**
 * @author Christophe Pollet
 */
public class Main {
    public static void main(String[] args) {
        AttributeService attributeService = BeanFactory.attributeService();
        Transformer transformer = BeanFactory.transformer();

        new ServiceConsumer(attributeService, transformer).run();
    }
}
