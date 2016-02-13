package net.cpollet.pocs.jersey.spring;

import net.cpollet.pocs.jersey.services.api.UserService;
import net.cpollet.pocs.jersey.services.impl.UserServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christophe Pollet
 */
@Configuration
public class ApplicationConfiguration {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }
}
