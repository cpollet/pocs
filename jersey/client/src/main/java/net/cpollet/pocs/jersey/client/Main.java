package net.cpollet.pocs.jersey.client;

import net.cpollet.pocs.jersey.client.service.UserService;
import net.cpollet.pocs.jersey.client.service.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Christophe Pollet
 */
public class Main {
    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("net.cpollet.pocs.jersey.client.spring");

        UserService userService = applicationContext.getBean(UserService.class);

        try {
            LOGGER.info(userService.getUser("cpollet").toString());
            LOGGER.info(userService.getUser("unknown").toString());
        }
        catch (UserNotFoundException e) {
            LOGGER.error("Error", e);
        }
    }
}
