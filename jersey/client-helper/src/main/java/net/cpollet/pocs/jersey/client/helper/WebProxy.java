package net.cpollet.pocs.jersey.client.helper;

import net.cpollet.pocs.jersey.rest.v1.api.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Christophe Pollet
 */
public class WebProxy implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebProxy.class);
    private final String baseUrl;
    private final RestClient restClient;
    private final ExceptionTranslator exceptionTranslator;


    public WebProxy(String baseUrl, RestClient restClient, ExceptionTranslator exceptionTranslator) {
        this.baseUrl = baseUrl;
        this.restClient = restClient;
        this.exceptionTranslator = exceptionTranslator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Path pathOnClass = method.getDeclaringClass().getAnnotation(Path.class);
        Path pathOnMethod = method.getAnnotation(Path.class);

        String path = baseUrl + "/" + pathOnClass.value() + "/" + pathOnMethod.value().replace("{username}", (CharSequence) args[0]);

        Response response = restClient.get(path);

        LOGGER.info("HTTP status: " + response.getStatus());

        switch (response.getStatus() / 100) {
            case 2:
                return response.readEntity(method.getReturnType());
            case 4:
            case 5:
                exceptionTranslator.translateException(response.readEntity(ErrorResponse.class));
                break;
        }

        throw new IllegalStateException("HTTP status [] not supported" + response.getStatus());
    }
}
