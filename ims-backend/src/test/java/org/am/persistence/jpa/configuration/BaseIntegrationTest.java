package org.am.persistence.jpa.configuration;

import lombok.extern.slf4j.Slf4j;
import org.am.fakers.Faker;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Testcontainers
@Slf4j
@SpringBootTest(classes = PersistenceConfiguration.class)
public class BaseIntegrationTest {

    @Container
    public static DatabaseContainer postgreSQLContainer = DatabaseContainer.getInstance();

    @Autowired
    protected Faker faker;

    @Autowired
    protected IntegrationTestPersister integrationTestPersister;

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @AfterEach
    void cleanup() {

        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        em.createNativeQuery("TRUNCATE warehouses CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE addresses CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE towns CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE counties CASCADE").executeUpdate();

        em.getTransaction().commit();

        em.close();
    }
}
