package org.am.persistence.jpa.configuration;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

public class BaseIntegrationTest {

    @Autowired
    protected Faker faker;

    @Autowired
    protected IntegrationTestPersister integrationTestPersister;

    @Autowired
    protected EntityManagerFactory entityManagerFactory;
}
