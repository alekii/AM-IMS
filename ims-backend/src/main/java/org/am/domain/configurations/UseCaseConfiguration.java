package org.am.domain.configurations;

import org.am.domain.api.GetWarehouseUseCase;
import org.am.domain.impl.GetWarehouseUseCaseImpl;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    GetWarehouseUseCase getBusinessClientUseCase(WarehouseDAO warehouseDAO) {

        return new GetWarehouseUseCaseImpl(warehouseDAO);
    }
}
