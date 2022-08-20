package org.am.persistence.jpa.configuration;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class DatabaseContainer extends PostgreSQLContainer<DatabaseContainer> {

    private static final String IMAGE_VERSION = "postgres:12.11-alpine";

    private static DatabaseContainer container;

    private DatabaseContainer() {

        super(IMAGE_VERSION);
    }

    public static DatabaseContainer getInstance() {

        if (container == null) {
            container = new DatabaseContainer();
        }
        return container;
    }

    @Override
    public void start() {

        container.withDatabaseName("ims");

        super.start();
    }

    @Override
    public void stop() {

        log.info("Container shutting down");
    }
}
