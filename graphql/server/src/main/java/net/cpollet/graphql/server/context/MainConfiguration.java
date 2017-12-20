package net.cpollet.graphql.server.context;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLTypeReference;
import net.cpollet.graphql.server.graphql.PortfolioFetcher;
import net.cpollet.graphql.server.graphql.Query;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class MainConfiguration {
    @Bean
    @ConditionalOnProperty("staticSchema")
    public GraphQLSchema staticGraphqlSchema() {
        return SchemaParser.newParser()
                .file("graphql/schema.graphqls")
                .resolvers(new Query())
                .build()
                .makeExecutableSchema();
    }

    @Bean
    @ConditionalOnMissingBean(GraphQLSchema.class)
    public GraphQLSchema dynamicGraphqlSchema() {
        return GraphQLSchema.newSchema()
                .query(GraphQLObjectType.newObject()
                        .name("Query")
                        .description("dynamic")
                        .field(GraphQLFieldDefinition.newFieldDefinition()
                                .name("allPortfolios")
                                .type(GraphQLList.list(new GraphQLTypeReference("Portfolio")))
                                .dataFetcher(new Query())
                        )
                        .build()
                )
                .additionalTypes(
                        new HashSet<>(Arrays.asList(
                                GraphQLObjectType.newObject()
                                        .name("Portfolio")
                                        .field(GraphQLFieldDefinition.newFieldDefinition()
                                                .name("id")
                                                .type(Scalars.GraphQLID)
                                                //.dataFetcher(new PortfolioFetcher())
                                                .build()
                                        )
                                        .field(GraphQLFieldDefinition.newFieldDefinition()
                                                .name("type")
                                                .type(Scalars.GraphQLString)
                                                //.dataFetcher(new PortfolioFetcher())
                                                .build()
                                        )
                                        .field(GraphQLFieldDefinition.newFieldDefinition()
                                                .name("other")
                                                .type(Scalars.GraphQLString)
                                                //.dataFetcher(new PortfolioFetcher())
                                                .build()
                                        )
                                        .build()
                        )
                        )
                ).build();
    }
}
