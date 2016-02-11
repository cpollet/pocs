package net.cpollet.pocs.readapi.helper;

/**
 * @author Christophe Pollet
 */
public class TransformationException extends RuntimeException {
    public TransformationException(Throwable cause) {
        super(cause);
    }

    public TransformationException(String message) {
        super(message);
    }
}
