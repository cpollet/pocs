package net.cpollet.pocs.jersey.rest.v1.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author Christophe Pollet
 */
public class RestException extends WebApplicationException {
    public static int ERROR_USER_NOT_FOUND = 1000;

    private final int code;

    public RestException(Exception cause, int code, Response.Status httpStatus) {
        super(cause.getMessage(), cause, httpStatus);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
