package org.example.test.util;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.DockerHealthcheckWaitStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

@Testcontainers
public abstract class TestContainers {

    private static final int MYSQL_PORT = 3306;

    @Container
    protected static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0.33") // specify image version
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withExposedPorts(MYSQL_PORT);

    private static AtomicBoolean containerStarted = new AtomicBoolean(false);


}
