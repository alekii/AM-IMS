package org.am.domain.configurations;

import org.am.domain.validators.WarehouseValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    WarehouseValidator warehouseValidator() {

        return new WarehouseValidator();
    }
}
