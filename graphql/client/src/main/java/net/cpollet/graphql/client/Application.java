package net.cpollet.graphql.client;

import com.google.gson.Gson;
import net.cpollet.graphql.client.graphql.GraphQLRequest;
import net.cpollet.graphql.client.graphql.GraphQLResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/graphql");

        GraphQLRequest request = new GraphQLRequest("{allPortfolios{id type}}",null,null);

        httpPost.setEntity(new StringEntity(gson.toJson(request)));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        String responsePayload = IOUtils.toString(client.execute(httpPost).getEntity().getContent());

        GraphQLResponse response = gson.fromJson(responsePayload, GraphQLResponse.class);
        response.getData().getAllPortfolios()
                .forEach(System.out::println);

        client.close();
    }
}
