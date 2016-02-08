package net.cpollet.pocs.tests;

/**
 * @author Christophe Pollet
 */
public class SystemPropertyConsumerImpl implements SystemPropertyConsumer {
    private final String defined;

    public SystemPropertyConsumerImpl() {
        this.defined = System.getProperty("defined");
    }

    @Override
    public String getDefined() {
        return defined;
    }
}
