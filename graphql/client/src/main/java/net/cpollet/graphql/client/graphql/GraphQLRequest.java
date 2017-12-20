package net.cpollet.graphql.client.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphQLRequest {
    private String query;
    private String operationName;
    private String variables;
}
