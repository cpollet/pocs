package net.cpollet.pocs.jersey.services.api;

/**
 * @author Christophe Pollet
 */
public interface UserService {
    User getUser(String username) throws UserNotFoundException;
}
