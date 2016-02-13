package net.cpollet.pocs.jersey.rest.v1.api.exceptions;

import net.cpollet.pocs.jersey.rest.v1.api.ErrorResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Christophe Pollet
 */
public class RestException extends WebApplicationException {
    public static int ERROR_USER_NOT_FOUND = 1000;

    public RestException(String message, int code) {
        super(Response.status(Response.Status.NOT_FOUND).
                entity(new ErrorResponse(message, code))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}
