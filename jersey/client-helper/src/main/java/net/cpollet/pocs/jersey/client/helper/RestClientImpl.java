package net.cpollet.pocs.jersey.client.helper;

import net.cpollet.pocs.jersey.helpers.providers.JsonHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class RestClientImpl implements RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClientImpl.class);

    private final String host;

    public RestClientImpl(String host) {
        this.host = host;
    }

    @Override
    public Response get(String url, Map<String, Object> pathParams, Map<String, Object> queryParams) {
        Client client = ClientBuilder.newClient();

        client.register(JsonHandler.class);

        WebTarget webTarget = client.target(host)
                .path(url)
                .resolveTemplates(pathParams);

        for (Map.Entry<String, Object> queryParam : queryParams.entrySet()) {
            webTarget = webTarget.queryParam(queryParam.getKey(), queryParam.getValue());
        }

        LOGGER.info("URL is " + webTarget.getUri());

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get();
    }
}
