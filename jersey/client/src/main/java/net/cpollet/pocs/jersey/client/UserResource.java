package net.cpollet.pocs.jersey.client;

import net.cpollet.pocs.jersey.client.service.exceptions.UserNotFoundException;
import net.cpollet.pocs.jersey.rest.v1.api.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Christophe Pollet
 */
@Path("users")
public interface UserResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{username}")
    User getUserInfo(@PathParam("username") String username) throws UserNotFoundException;
}
