package net.cpollet.graphql.server.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.TypeResolutionEnvironment;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import net.cpollet.graphql.server.domain.Portfolio;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Query implements GraphQLQueryResolver, DataFetcher<List<Portfolio>> {
    public List<Portfolio> getAllPortfolios() {
        return Arrays.asList(
                new Portfolio("id-1", "trading"),
                new Portfolio("id-2", "saving")
        );
    }

    @Override
    public List<Portfolio> get(DataFetchingEnvironment environment) {
        String uuid = UUID.randomUUID().toString();

        environment.getFields().forEach(f -> System.out.println(uuid + " - fetching " + f.getName()));
        environment.getFields().get(0).getSelectionSet().getSelections().forEach(s -> System.out.println(uuid + " -  >" + s));

        return getAllPortfolios();
    }
}
