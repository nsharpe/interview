package org.example.test.util;

import org.example.media.management.sdk.invoker.ApiClient;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

public abstract class TestContainers {

    private static final int REDIS_PORT = 6379;

    protected static final DockerComposeContainer<?> ENVIRONMENT =
            new DockerComposeContainer<>(new File("../docker-compose.yml"),
                    new File("../docker-compose.stack.yml"))
                    .withExposedService("mysql", 3306, Wait.forListeningPort()
                            .withStartupTimeout(Duration.ofMinutes(3)))
                    .withExposedService("redis", REDIS_PORT, Wait.forListeningPort())
                    .withExposedService("broker", 9092, Wait.forListeningPort())
                    .withExposedService("public-rest", 9080,
                            Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1))
                    .withExposedService("media-management", 9090,
                            Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1)
                    )
                    .withEnv("COMPOSE_PROJECT_NAME", "test-project")
                    .withLocalCompose(true)
                    .withBuild(true)
                    .withStartupTimeout(Duration.ofMinutes(3))
                    .withOptions("--compatibility")
                    .withLogConsumer("docker-compose", new Slf4jLogConsumer(LoggerFactory.getLogger("DockerCompose")));

    static {
        ENVIRONMENT.start();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("publicrest.port", () -> ENVIRONMENT.getServicePort("public-rest", 9080));
        registry.add("publicrest.host", () -> "localhost");

        registry.add("media.management.port", () -> ENVIRONMENT.getServicePort("media-management", 9090));
        registry.add("media.management.host", () -> "localhost");

        registry.add("spring.data.redis.port", () -> ENVIRONMENT.getServicePort("redis", REDIS_PORT));
        registry.add("spring.data.redis.host", () -> "localhost");
        registry.add("spring.data.redis.username", () -> "default");
        registry.add("spring.data.redis.password", () -> "password");
    }

    // Helper methods for the ApiClients
    public ApiClient managementApiClient() {
        return new ApiClient(WebClient.builder().build())
                .setBasePath("http://localhost:" + getMediaManagmentPort());
    }

    public int getMediaManagmentPort(){
        return ENVIRONMENT.getServicePort("media-management", 9090);
    }

    public org.example.publicrest.sdk.invoker.ApiClient publicRestApiClient() {
        return new org.example.publicrest.sdk.invoker.ApiClient(WebClient.builder().build())
                .setBasePath("http://localhost:" + getPublicRestPort());
    }

    public int getPublicRestPort(){
        return ENVIRONMENT.getServicePort("public-rest", 9080);
    }

}
