package org.am.infrastructure.persistence;

import org.am.infrastructure.warehouses.WarehouseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        WarehouseRepository.class
})
@EnableTransactionManagement
public class JPAConfiguration {

    @Bean
    public ProjectionFactory projectionFactory() {

        return new SpelAwareProxyProjectionFactory();
    }
}
