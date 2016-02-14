package net.cpollet.pocs.jersey.rest.v1.resources;

import net.cpollet.pocs.jersey.rest.v1.api.User;
import net.cpollet.pocs.jersey.rest.v1.exceptions.RestException;
import net.cpollet.pocs.jersey.services.api.UserNotFoundException;
import net.cpollet.pocs.jersey.services.api.UserService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "users" path)
 */
@Path("users")
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private static final String USERNAME = "username";

    private final UserService userService;
    private final Mapper mapper;

    @Inject
    public UserResource(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{" + USERNAME + "}")
    public User getUserInfo(@PathParam(USERNAME) String username) {
        LOGGER.info("In " + this);

        try {
            return mapper.map(userService.getUser(username), User.class);
        }
        catch (UserNotFoundException e) {
            throw new RestException(e.getMessage(), RestException.ERROR_USER_NOT_FOUND);
        }
    }
}
