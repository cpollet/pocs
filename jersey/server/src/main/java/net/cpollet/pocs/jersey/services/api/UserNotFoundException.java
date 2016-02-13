package net.cpollet.pocs.jersey.services.api;

/**
 * @author Christophe Pollet
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String username) {
        super(String.format("Username [%s] not found", username));
    }
}
