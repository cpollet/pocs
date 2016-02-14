package net.cpollet.pocs.jersey.client.helper;

import javax.ws.rs.core.Response;

/**
 * @author Christophe Pollet
 */
public interface RestClient {
    Response get(String url);
}
