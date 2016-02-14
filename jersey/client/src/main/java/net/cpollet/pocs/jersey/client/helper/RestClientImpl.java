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
    public Response get(String url) {
        Client client = ClientBuilder.newClient();

        client.register(JsonHandler.class);

        WebTarget webTarget = client.target(host);
        WebTarget resourceWebTarget = webTarget.path(url);

        LOGGER.info("URL is " + resourceWebTarget.getUri());

        Invocation.Builder invocationBuilder = resourceWebTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get();
    }
}
