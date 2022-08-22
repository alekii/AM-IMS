package org.am.persistence.jpa.configuration;

import org.am.fakers.FakerIT;
import org.am.infrastructure.persistence.Configuration.DataSourceConfiguration;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@ComponentScan(value = {"org.am.persistence.jpa", "org.am.infrastructure"})
@EnableTransactionManagement
@EnableAutoConfiguration
@ContextConfiguration(classes = {DataSourceConfiguration.class})
public class PersistenceConfiguration {

    @Bean
    public FakerIT fakerIT() {

        return new FakerIT();
    }

    @ConditionalOnMissingBean(FlywayMigrationStrategy.class)
    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {

        return Flyway::migrate;
    }
}
