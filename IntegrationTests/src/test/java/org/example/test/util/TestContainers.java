package org.example.test.util;

import com.redis.testcontainers.RedisContainer;
import org.example.media.management.sdk.invoker.ApiClient;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@DirtiesContext
@Testcontainers
public abstract class TestContainers {

    private static final int MYSQL_PORT = 3306;

    private static Network NETWORK = Network.newNetwork();

    @Container
    protected static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0.33") // specify image version
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withExposedPorts(MYSQL_PORT)
            .withNetwork(NETWORK)
            .withNetworkAliases("mysql-db")
            .withStartupTimeout(Duration.ofMinutes(1))
            .waitingFor(Wait.forListeningPort())
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("Log-mysql")));

    @Container
    protected static final RedisContainer REDIS_CONTAINER = new RedisContainer("redis:8.4.0")
            .withNetwork(NETWORK)
            .withNetworkAliases("redis")
            .withCommand("redis-server --requirepass password")
            .waitingFor(Wait.forListeningPort());

    @Container
    protected static final GenericContainer<?> PUBLIC_REST_CONTAINER = springWebContainer(TestImages.PUBLIC_REST_ENDPOINT_IMAGE, "public-rest-service")
            .dependsOn(MYSQL_CONTAINER);

    @Container
    protected static final GenericContainer<?> MEDIA_MANAGEMENT_CONTAINER = springWebContainer(TestImages.MEDIA_MANAGEMENT_IMAGE, "media-management")
            .dependsOn(MYSQL_CONTAINER);


    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("publicrest.port", () -> PUBLIC_REST_CONTAINER.getMappedPort(8080));
        registry.add("publicrest.host", () -> "localhost");
        registry.add("media.management.port", () -> MEDIA_MANAGEMENT_CONTAINER.getMappedPort(8080));
        registry.add("media.management.host", () -> "localhost");
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379));
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.username", () -> "default");
        registry.add("spring.data.redis.password", () -> "password");
    }

    public static void start() {
        try {
            Startables.deepStart(
                    MYSQL_CONTAINER,
                    MEDIA_MANAGEMENT_CONTAINER,
                    PUBLIC_REST_CONTAINER,
                    REDIS_CONTAINER
            ).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static GenericContainer<?> springWebContainer(ImageFromDockerfile imageFromDockerfile, String alias) {
        return new GenericContainer<>(
                imageFromDockerfile
        ).withExposedPorts(8080, 8081)
                .withEnv("SERVER_ADDRESS", "0.0.0.0")
                .withEnv("MYSQL_HOST", "mysql-db")
                .withEnv("MYSQL_USER","testuser")
                .withEnv("MYSQL_DATABASE","testdb")
                .withEnv("MYSQL_PASSWORD","testpass")
                .withEnv("SPRING_JPA_SHOW_SQL", "true")
                .withEnv("REDIS_HOST", "redis")
                .withNetwork(NETWORK)
                .withEnv("MANAGEMENT_SERVER_ADDRESS", "0.0.0.0")
                .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("Log-" + alias)))
                .waitingFor(Wait.forLogMessage(".*Started .* in .* seconds.*\\n", 1))
                .withNetworkAliases(alias)
                .dependsOn(REDIS_CONTAINER)
                .withStartupTimeout(Duration.ofSeconds(60));
    }

    public ApiClient managementApiClient() {

        return new ApiClient(
                WebClient.builder()
                        .build()
        ).setBasePath("http://localhost:" + MEDIA_MANAGEMENT_CONTAINER.getMappedPort(8080));
    }

    public org.example.publicrest.sdk.invoker.ApiClient publicRestApiClient() {

        return new org.example.publicrest.sdk.invoker.ApiClient(
                WebClient.builder()
                        .build()
        ).setBasePath("http://localhost:" + PUBLIC_REST_CONTAINER.getMappedPort(8080));
    }

}
