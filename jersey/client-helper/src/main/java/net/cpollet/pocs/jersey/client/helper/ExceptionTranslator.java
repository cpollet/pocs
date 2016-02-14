package net.cpollet.pocs.jersey.client.helper;

import net.cpollet.pocs.jersey.rest.v1.api.ErrorResponse;

/**
 * @author Christophe Pollet
 */
public interface ExceptionTranslator {
    void translateException(ErrorResponse errorResponse) throws Exception;
}
