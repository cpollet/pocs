package net.cpollet.pocs.read.client.context;

/**
 * @author Christophe Pollet
 */
public class StartupException extends RuntimeException {
    public StartupException(Exception e) {
        super(e);
    }
}
