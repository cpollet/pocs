package net.cpollet.graphql.client.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.cpollet.graphql.client.domain.Portfolio;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphQLData {
    private List<Portfolio> allPortfolios;
}
