package net.cpollet.pocs.jersey.client.helper;

import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @author Christophe Pollet
 */
public interface RestClient {
    Response get(String url, Map<String, Object> pathParams, Map<String, Object> queryParams);
}
