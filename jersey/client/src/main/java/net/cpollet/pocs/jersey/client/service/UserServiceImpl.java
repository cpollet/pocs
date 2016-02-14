package net.cpollet.pocs.jersey.client.service;

import net.cpollet.pocs.jersey.client.UserResource;
import net.cpollet.pocs.jersey.client.service.exceptions.UserNotFoundException;
import net.cpollet.pocs.jersey.rest.v1.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christophe Pollet
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserResource userResource;

    public UserServiceImpl(UserResource userResource) {
        this.userResource = userResource;
    }

    @Override
    public User getUser(String username) throws UserNotFoundException {
        return userResource.getUserInfo(username);
    }
}
