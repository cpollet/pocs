package net.cpollet.pocs.jersey.services.impl;

import net.cpollet.pocs.jersey.services.api.User;
import net.cpollet.pocs.jersey.services.api.UserNotFoundException;
import net.cpollet.pocs.jersey.services.api.UserService;

import java.time.LocalDate;

/**
 * @author Christophe Pollet
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(String username) throws UserNotFoundException {
        if (username.equals("cpollet")) {
            return new User(true, "cpollet@users.noreply.github.com", "cpollet", LocalDate.of(2016, 1, 3));
        }

        throw new UserNotFoundException(username);
    }
}
