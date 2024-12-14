package com.example.cosmocatsmarketplace;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public abstract class AbstractIt {
    private static final int DB_PORT = 5432;

    static final GenericContainer DB_CONTAINER = new GenericContainer("postgres:latest")
            .withEnv("POSTGRES_PASSWORD", "postgres")
            .withEnv("POSTGRES_DB", "postgres").withExposedPorts(DB_PORT);

    static {
        DB_CONTAINER.start();
    }

    @DynamicPropertySource
    static void setupTestContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://%s:%d/postgres",
                DB_CONTAINER.getHost(), DB_CONTAINER.getMappedPort(DB_PORT)));
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "postgres");
    }
}
