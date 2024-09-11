package com.testcontainers.fun.cantspellAI;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.testcontainers.ollama.OllamaContainer;
import static org.assertj.core.api.Assertions.assertThat;

public class OllamaContainerTest extends Simulation {

    @Test
    public void withDefaultConfig() {
        try (OllamaContainer ollama = new OllamaContainer("ollama/ollama:0.1.40")) {
            ollama.start();

            String version = RestAssured.given()
                    .baseUri(ollama.getEndpoint())
                    .get("/api/version")
                    .jsonPath()
                    .get("version");

            assertThat(version).isEqualTo("0.1.40");

            HttpProtocolBuilder httpProtocol =
                http.baseUrl("https://computer-database.gatling.io")
                .acceptHeader("application/json")
                .contentTypeHeader("application/json");


                
            ScenarioBuilder myFirstScenario = scenario("My First Scenario")
                .exec(http("Request 1").get("/computers/"));

            setUp(
                myFirstScenario.injectOpen(constantUsersPerSec(2).during(60))
            ).protocols(httpProtocol);
        }
    }
}