package net.cpollet.pocs.jersey.client;

import net.cpollet.pocs.jersey.client.helper.ExceptionTranslator;
import net.cpollet.pocs.jersey.client.service.exceptions.UserNotFoundException;
import net.cpollet.pocs.jersey.rest.v1.api.ErrorResponse;

/**
 * @author Christophe Pollet
 */
public class UserExceptionTranslator implements ExceptionTranslator{
    @Override
    public void translateException(ErrorResponse errorResponse) throws Exception {
        switch (errorResponse.getCode()) {
            case 1000:
                throw new UserNotFoundException(errorResponse.getMessage());
            default:
                throw new RuntimeException("ERR-" + errorResponse.getCode() + " - " + errorResponse.getMessage());
        }
    }
}
