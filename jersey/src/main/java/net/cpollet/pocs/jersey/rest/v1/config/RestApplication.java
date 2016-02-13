package net.cpollet.pocs.jersey.rest.v1.config;

import net.cpollet.pocs.jersey.rest.providers.JsonHandler;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author Christophe Pollet
 */
@ApplicationPath("/api/v1")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        packages("net.cpollet.pocs.jersey.rest.v1");
        register(JsonHandler.class);
    }
}
