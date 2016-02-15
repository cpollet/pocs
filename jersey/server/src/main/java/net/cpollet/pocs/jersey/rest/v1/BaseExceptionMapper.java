package net.cpollet.pocs.jersey.rest.v1;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christophe Pollet
 */
public abstract class BaseExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {
    private static final List<MediaType> supportedMediaTypes = new ArrayList<MediaType>() {{
        add(MediaType.APPLICATION_JSON_TYPE);
    }};

    @Context
    private HttpHeaders headers;

    protected MediaType getMediaType() {
        return headers.getAcceptableMediaTypes().stream()
                .filter(supportedMediaTypes::contains)
                .findFirst()
                .orElse(MediaType.APPLICATION_JSON_TYPE);
    }
}
