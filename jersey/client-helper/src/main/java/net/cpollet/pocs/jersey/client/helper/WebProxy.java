package net.cpollet.pocs.jersey.client.helper;

import net.cpollet.pocs.jersey.rest.v1.api.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

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

        String methodUri = pathOnMethod.value();

        Parameter[] parameters = method.getParameters();
        Map<String, Object> pathParams = new HashMap<>();
        Map<String, Object> queryParams = new HashMap<>();

        for (int i = 0; i < parameters.length; i++) {
            PathParam pathParam = parameters[i].getAnnotation(PathParam.class);
            if (pathParam != null) {
                pathParams.put(pathParam.value(), args[i]);
            }
            QueryParam queryParam = parameters[i].getAnnotation(QueryParam.class);
            if (queryParam != null) {
                queryParams.put(queryParam.value(), args[i]);
            }
        }

        String path = baseUrl + "/" + pathOnClass.value() + "/" + methodUri;

        LOGGER.info("HTTP template URI: {}", path);

        Response response = restClient.get(path, pathParams, queryParams);

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
