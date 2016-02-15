package net.cpollet.pocs.jersey.rest.v1.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author Christophe Pollet
 */
@ApplicationPath("/api/v1")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        packages("net.cpollet.pocs.jersey.rest.v1");
        register(JacksonFeature.class);
    }
}
