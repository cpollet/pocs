package net.cpollet.pocs.jersey.client.service;

import net.cpollet.pocs.jersey.client.service.exceptions.UserNotFoundException;
import net.cpollet.pocs.jersey.rest.v1.api.User;

/**
 * @author Christophe Pollet
 */
public interface UserService {
    User getUser(String username) throws UserNotFoundException;
}
