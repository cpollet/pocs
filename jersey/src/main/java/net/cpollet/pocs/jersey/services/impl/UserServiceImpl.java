package net.cpollet.pocs.jersey.services.impl;

import net.cpollet.pocs.jersey.services.api.User;
import net.cpollet.pocs.jersey.services.api.UserNotFoundException;
import net.cpollet.pocs.jersey.services.api.UserService;

/**
 * @author Christophe Pollet
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(String username) throws UserNotFoundException {
        if (username.equals("cpollet")) {
            return new User(true, "cpollet@users.noreply.github.com", "cpollet");
        }

        throw new UserNotFoundException(username);
    }
}
