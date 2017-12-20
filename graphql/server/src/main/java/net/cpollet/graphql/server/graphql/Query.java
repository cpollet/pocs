package net.cpollet.graphql.server.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import net.cpollet.graphql.server.domain.Portfolio;

import java.util.Arrays;
import java.util.List;

public class Query implements GraphQLQueryResolver {
    public List<Portfolio> getAllPortfolios() {
        return Arrays.asList(
                new Portfolio("id-1", "trading"),
                new Portfolio("id-2", "saving")
        );
    }
}