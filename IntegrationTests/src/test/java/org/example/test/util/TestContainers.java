package org.example.test.util;

import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

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
            .waitingFor(Wait.forLogMessage(".*ready for connections.*port: 3306.*\\n", 1));

    @Container
    protected static final GenericContainer<?> PUBLIC_REST_CONTAINER = springWebContainer(TestImages.PUBLIC_REST_ENDPOINT_IMAGE);

    @Container
    protected static final GenericContainer<?> MEDIA_MANAGEMENT_CONTAINER = springWebContainer(TestImages.MEDIA_MANAGEMENT_IMAGE);

    private static GenericContainer<?> springWebContainer(ImageFromDockerfile imageFromDockerfile){
        return new GenericContainer<>(
                imageFromDockerfile
        ).withExposedPorts(8080,8081)
                .withEnv("SERVER_ADDRESS", "0.0.0.0")
                .withEnv("SPRING_PROFILE", "integration")
                .withEnv("MYSQL_HOST","mysql-db")
                .withEnv("SPRING_JPA_SHOW_SQL","true")
                .withNetwork(NETWORK)
                .withEnv("MANAGEMENT_SERVER_ADDRESS", "0.0.0.0")
                .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("Docker-Build")))
                .waitingFor(Wait.forLogMessage(".*Tomcat started on port 808.*\\n", 2))
                .withStartupTimeout(Duration.ofSeconds(60));
    }


}
