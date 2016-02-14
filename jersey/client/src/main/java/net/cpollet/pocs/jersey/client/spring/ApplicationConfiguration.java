package net.cpollet.pocs.jersey.client.spring;

import net.cpollet.pocs.jersey.client.UserExceptionTranslator;
import net.cpollet.pocs.jersey.client.UserResource;
import net.cpollet.pocs.jersey.client.helper.ExceptionTranslator;
import net.cpollet.pocs.jersey.client.helper.RestClient;
import net.cpollet.pocs.jersey.client.helper.RestClientImpl;
import net.cpollet.pocs.jersey.client.helper.WebProxy;
import net.cpollet.pocs.jersey.client.service.UserService;
import net.cpollet.pocs.jersey.client.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

/**
 * @author Christophe Pollet
 */
@Configuration
public class ApplicationConfiguration {
    @Bean
    public UserService userService() {
        return new UserServiceImpl(userResource());
    }

    @Bean
    public UserResource userResource() {
        return (UserResource) Proxy.newProxyInstance(
                UserResource.class.getClassLoader(),
                new Class[]{UserResource.class},
                new WebProxy("api/v1", restClient(), userExceptionTranslator()));
    }

    @Bean
    public RestClient restClient() {
        return new RestClientImpl("http://localhost:8080");
    }

    @Bean
    public ExceptionTranslator userExceptionTranslator() {
        return new UserExceptionTranslator();
    }
}
