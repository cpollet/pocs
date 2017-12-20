package net.cpollet.graphql.server.context;

import com.coxautodev.graphql.tools.SchemaParser;
import net.cpollet.graphql.server.graphql.Query;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {
    @Bean
    public SchemaParser graphqlSchema() {
        return SchemaParser.newParser()
                .file("graphql/schema.graphqls")
                .resolvers(new Query())
                .build();
    }
}
