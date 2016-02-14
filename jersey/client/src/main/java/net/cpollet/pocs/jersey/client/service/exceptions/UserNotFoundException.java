package net.cpollet.pocs.jersey.client.service.exceptions;

/**
 * @author Christophe Pollet
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
