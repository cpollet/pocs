package net.cpollet.pocs.jersey.rest.v1;

import net.cpollet.pocs.jersey.rest.v1.api.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author Christophe Pollet
 */
@Provider
public class WebApplicationExceptionMapper extends BaseExceptionMapper<WebApplicationException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

    @Override
    public Response toResponse(WebApplicationException exception) {
        LOGGER.info("We got an exception with message: '{}'", exception.getMessage(), exception);
        return Response.status(exception.getResponse().getStatus()).
                entity(new ErrorResponse(exception.getMessage(), exception.getResponse().getStatus()))
                .type(getMediaType())
                .build();
    }
}
