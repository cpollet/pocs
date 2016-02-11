package net.cpollet.pocs.read.helper;

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
