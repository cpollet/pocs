package net.cpollet.read.v2.client;

import com.google.gson.GsonBuilder;
import net.cpollet.read.v2.api.attribute.AttributeStore;
import net.cpollet.read.v2.api.attribute.printer.AttributeMetadataPrinter;
import net.cpollet.read.v2.api.execution.Executor;
import net.cpollet.read.v2.api.execution.Request;
import net.cpollet.read.v2.client.domain.PortfolioId;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        @SuppressWarnings("unchecked") Executor<PortfolioId> portfolioExecutor = (Executor<PortfolioId>) context.getBean("portfolio.executor");
        @SuppressWarnings("unchecked") AttributeStore<PortfolioId> portfolioAttributeStore = (AttributeStore<PortfolioId>) context.getBean("portfolio.attributeStore");

        printMetadata(portfolioAttributeStore);
        read(portfolioExecutor);
        write(portfolioExecutor);
        delete(portfolioExecutor);
        create(portfolioExecutor);
    }

    private static void printMetadata(AttributeStore<PortfolioId> portfolioAttributeStore) {
        System.out.println("-- METADATA ------");
        System.out.println(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(
                                portfolioAttributeStore.attributes().stream()
                                        .map(a -> a.print(new AttributeMetadataPrinter()))
                                        .collect(Collectors.toList())
                        )
        );
    }

    private static void read(Executor<PortfolioId> portfolioExecutor) {
        System.out.println("-- READ ------");
        System.out.println(
                portfolioExecutor.read(
                        Request.read(
                                Arrays.asList(
                                        new PortfolioId("100000"),
                                        new PortfolioId("200000"),
                                        new PortfolioId("999999")
                                ),
                                Arrays.asList(
                                        "id",
                                        "status",
                                        "ownerId",
                                        "currency",
                                        "unknown",
                                        "owner.email",
                                        "owner.unknown",
                                        "owner.portfolioId",
                                        "owner.address.street"
                                )
                        )
                )
        );
        System.out.println(
                portfolioExecutor.read(
                        Request.read(
                                Collections.singletonList(new PortfolioId("200000")),
                                Collections.singletonList("*")
                        )
                )
        );
    }

    private static void write(Executor<PortfolioId> portfolioExecutor) {
        System.out.println("-- WRITE ------");
        Map<String, Object> attributesValues = new HashMap<>();
        attributesValues.put("status", 40);
        attributesValues.put("ownerId", 123123);
        attributesValues.put("currency", "CHF");
        attributesValues.put("hidden", "hidden");
        attributesValues.put("unknown", "?");

        System.out.println(
                portfolioExecutor.update(
                        Request.write(
                                Arrays.asList(
                                        new PortfolioId("100000"),
                                        new PortfolioId("200000"),
                                        new PortfolioId("999999")
                                ),
                                attributesValues
                        )
                )
        );
    }

    private static void delete(Executor<PortfolioId> portfolioExecutor) {
        System.out.println("-- DELETE ------");
        System.out.println(
                portfolioExecutor.delete(
                        Request.delete(
                                Arrays.asList(
                                        new PortfolioId("100000"),
                                        new PortfolioId("200000"),
                                        new PortfolioId("999999")
                                ),
                                Arrays.asList(
                                        "id",
                                        "status"
                                )
                        )
                )
        );
    }

    private static void create(Executor<PortfolioId> portfolioExecutor) {
        System.out.println("-- CREATE ------");
        Map<String, Object> attributesValues = new HashMap<>();
        attributesValues.put("status", 40);
        attributesValues.put("ownerId", 123123);

        System.out.println(
                portfolioExecutor.create(
                        Request.create(
                                attributesValues
                        )
                )
        );
    }
}
