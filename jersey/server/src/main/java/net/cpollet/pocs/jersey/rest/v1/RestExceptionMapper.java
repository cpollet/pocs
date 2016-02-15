package net.cpollet.pocs.jersey.rest.v1;

import net.cpollet.pocs.jersey.rest.v1.api.ErrorResponse;
import net.cpollet.pocs.jersey.rest.v1.exceptions.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author Christophe Pollet
 */
@Provider
public class RestExceptionMapper extends BaseExceptionMapper<RestException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionMapper.class);

    public Response toResponse(RestException exception) {
        LOGGER.info("We got an exception with message: '{}'", exception.getMessage(), exception);
        return Response.status(exception.getResponse().getStatus()).
                entity(new ErrorResponse(exception.getMessage(), exception.getCode()))
                .type(getMediaType())
                .build();
    }
}

