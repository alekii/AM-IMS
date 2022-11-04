package org.am.domain.configurations;

import org.am.domain.api.CreateSupplierUseCase;
import org.am.domain.api.CreateWarehouseUseCase;
import org.am.domain.api.GetSupplierUseCase;
import org.am.domain.api.GetWarehouseUseCase;
import org.am.domain.api.UpdateSupplierUseCase;
import org.am.domain.api.UpdateWarehouseUseCase;
import org.am.domain.impl.CreateSupplierUseCaseImpl;
import org.am.domain.impl.CreateWarehouseUseCaseImpl;
import org.am.domain.impl.GetSupplierUseCaseImpl;
import org.am.domain.impl.GetWarehouseUseCaseImpl;
import org.am.domain.impl.UpdateSupplierUseCaseImpl;
import org.am.domain.impl.UpdateWarehouseUseCaseImpl;
import org.am.domain.validation.validators.SupplierValidator;
import org.am.domain.validation.validators.WarehouseValidator;
import org.am.infrastructure.persistence.api.SupplierDAO;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    GetWarehouseUseCase getBusinessClientUseCase(WarehouseDAO warehouseDAO) {

        return new GetWarehouseUseCaseImpl(warehouseDAO);
    }

    @Bean
    CreateWarehouseUseCase createWarehouseUseCase(WarehouseDAO warehouseDAO, WarehouseValidator warehouseValidator) {

        return new CreateWarehouseUseCaseImpl(warehouseDAO, warehouseValidator);
    }

    @Bean
    UpdateWarehouseUseCase updateWarehouseUseCase(WarehouseDAO warehouseDAO, WarehouseValidator warehouseValidator) {

        return new UpdateWarehouseUseCaseImpl(warehouseDAO, warehouseValidator);
    }

    @Bean
    CreateSupplierUseCase createSupplierUseCase(SupplierDAO supplierDAO, SupplierValidator supplierValidator) {

        return new CreateSupplierUseCaseImpl(supplierDAO, supplierValidator);
    }

    @Bean
    GetSupplierUseCase getSupplierUseCase(SupplierDAO supplierDAO) {

        return new GetSupplierUseCaseImpl(supplierDAO);
    }

    @Bean
    UpdateSupplierUseCase updateSupplierUseCase(SupplierDAO supplierDAO, SupplierValidator supplierValidator) {

        return new UpdateSupplierUseCaseImpl(supplierDAO, supplierValidator);
    }
}
