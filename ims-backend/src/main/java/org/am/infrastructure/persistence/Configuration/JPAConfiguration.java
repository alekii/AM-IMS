package org.am.infrastructure.persistence.Configuration;

import org.am.infrastructure.Address.AddressRepository;
import org.am.infrastructure.Towns.TownRepository;
import org.am.infrastructure.products.ProductRepository;
import org.am.infrastructure.suppliers.SuppplierRepository;
import org.am.infrastructure.warehouses.WarehouseRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        WarehouseRepository.class,
        AddressRepository.class,
        TownRepository.class,
        SuppplierRepository.class,
        ProductRepository.class
})

@EntityScan("org.am.library.entities")
@EnableTransactionManagement
public class JPAConfiguration {

    @Bean
    public ProjectionFactory projectionFactory() {

        return new SpelAwareProxyProjectionFactory();
    }
}
