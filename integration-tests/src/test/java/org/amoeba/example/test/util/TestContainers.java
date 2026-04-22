package org.amoeba.example.test.util;

import org.amoeba.example.media.management.sdk.invoker.ApiClient;
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
                    .withExposedService("user-management", 9080,
                            Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1))
                    .withExposedService("media-management", 9090,
                            Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1)
                    ).withExposedService("qa-endpoint-app", 9120,
                            Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1)
                    ).withExposedService("media-metrics", 8080,
                            Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1))
                    .withExposedService("media-comment", 8080,
                                         Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1))
                    .withExposedService("postgres", 5432, Wait.forListeningPort())

                    .withExposedService("media-player-endpoint",9100,
                            Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1))
                    .withExposedService("admin-app", 9110,
                            Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1))
                    .withEnv("COMPOSE_PROJECT_NAME", "test-project")
                    .withLocalCompose(true)
                    .withBuild(true)
                    .withStartupTimeout(Duration.ofMinutes(3))
                    .withOptions("--compatibility")
                    .withLogConsumer("media-player-endpoint", new Slf4jLogConsumer(LoggerFactory.getLogger("MediaPlayerEndpoint")))
                    .withLogConsumer("user-management", new Slf4jLogConsumer(LoggerFactory.getLogger("UserManagement")))
                    .withLogConsumer("admin-app", new Slf4jLogConsumer(LoggerFactory.getLogger("AdminApp")))
                    .withLogConsumer("qa-endpoint-app", new Slf4jLogConsumer(LoggerFactory.getLogger("QaEndpoint")))
                    .withLogConsumer("media-management", new Slf4jLogConsumer(LoggerFactory.getLogger("MediaManagement")))
                    .withLogConsumer("media-comment", new Slf4jLogConsumer(LoggerFactory.getLogger("MediaComment")));

    static {
        ENVIRONMENT.start();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("publicrest.port", TestContainers::getPublicRestPort);
        registry.add("publicrest.host", () -> ENVIRONMENT.getServiceHost("user-management", 9080));

        registry.add("admin.endpoint.port", TestContainers::getAdminPort);
        registry.add("admin.endpoint.host", TestContainers::getAdminHost);

        registry.add("qa.endpoint.port", TestContainers::getQaPort);
        registry.add("qa.endpoint.host", TestContainers::getQaHost);

        registry.add("media.management.port", TestContainers::getMediaManagmentPort);
        registry.add("media.management.host", () -> "localhost");

        registry.add("media.metric.host", () -> "localhost");
        registry.add("media.metric.port", TestContainers::getMediaMetricsPort);

        registry.add("spring.data.redis.port", () -> ENVIRONMENT.getServicePort("redis", REDIS_PORT));
        registry.add("spring.data.redis.host", () -> "localhost");
        registry.add("spring.data.redis.username", () -> "default");
        registry.add("spring.data.redis.password", () -> "password");
    }

    // Helper methods for the ApiClients
    public static ApiClient managementApiClient() {
        return new ApiClient(WebClient.builder().build())
                .setBasePath("http://localhost:" + getMediaManagmentPort());
    }

    public static int getMediaManagmentPort(){
        return ENVIRONMENT.getServicePort("media-management", 9090);
    }

    public static org.amoeba.example.apps.user_management.sdk.invoker.ApiClient publicRestApiClient() {
        return new org.amoeba.example.apps.user_management.sdk.invoker.ApiClient(WebClient.builder().build())
                .setBasePath("http://localhost:" + getPublicRestPort());
    }

    public static int getPublicRestPort(){
        return ENVIRONMENT.getServicePort("user-management", 9080);
    }

    public static String getPublicRestHost(){
        return ENVIRONMENT.getServiceHost("user-management", 9080);
    }

    public static org.amoeba.example.media.player.sdk.invoker.ApiClient mediaPlayerApiClient() {
        return new org.amoeba.example.media.player.sdk.invoker.ApiClient(WebClient.builder().build())
                .setBasePath("http://"+getPublicRestHost()+":" + getMediaPlayPort());
    }

    public static org.amoeba.example.media.metric.sdk.invoker.ApiClient getMediaMetricsApiClient(){
        return new org.amoeba.example.media.metric.sdk.invoker.ApiClient(WebClient.builder().build())
                .setBasePath("http://localhost:" + getMediaMetricsPort());
    }

    public static int getMediaMetricsPort(){
        return ENVIRONMENT.getServicePort("media-metrics", 8080);
    }

    public static int getMediaPlayPort(){
        return ENVIRONMENT.getServicePort("media-player-endpoint", 9100);
    }

    public static int getMediaCommentPort(){
        return ENVIRONMENT.getServicePort("media-comment", 8080);
    }

    public static String getAdminHost(){
        return ENVIRONMENT.getServiceHost("admin-app", 9110);
    }

    public static int getAdminPort(){
        return ENVIRONMENT.getServicePort("admin-app", 9110);
    }

    public static String getQaHost(){
        return ENVIRONMENT.getServiceHost("qa-endpoint-app", 9120);
    }

    public static int getQaPort(){
        return ENVIRONMENT.getServicePort("qa-endpoint-app", 9120);
    }

}
