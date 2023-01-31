package org.am.domain.configurations;

import org.am.domain.api.CreateBrandUseCase;
import org.am.domain.api.CreateCategoryUseCase;
import org.am.domain.api.CreateProductImageUseCase;
import org.am.domain.api.CreateProductUseCase;
import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.api.CreateSupplierUseCase;
import org.am.domain.api.CreateWarehouseUseCase;
import org.am.domain.api.DeleteProductImageUseCase;
import org.am.domain.api.GetBrandUseCase;
import org.am.domain.api.GetCategoryUseCase;
import org.am.domain.api.GetProductImageUseCase;
import org.am.domain.api.GetProductUseCase;
import org.am.domain.api.GetPurchaseUseCase;
import org.am.domain.api.GetSupplierUseCase;
import org.am.domain.api.GetWarehouseUseCase;
import org.am.domain.api.UpdateProductUseCase;
import org.am.domain.api.UpdatePurchaseUseCase;
import org.am.domain.api.UpdateSupplierUseCase;
import org.am.domain.api.UpdateWarehouseUseCase;
import org.am.domain.impl.CreateBrandUseCaseImpl;
import org.am.domain.impl.CreateCategoryUseCaseImpl;
import org.am.domain.impl.CreateProductImageUseCaseImpl;
import org.am.domain.impl.CreateProductUseCaseImpl;
import org.am.domain.impl.CreatePurchaseUseCaseImpl;
import org.am.domain.impl.CreateSupplierUseCaseImpl;
import org.am.domain.impl.CreateWarehouseUseCaseImpl;
import org.am.domain.impl.DeleteProductImageUseCaseImpl;
import org.am.domain.impl.GetBrandUseCaseImpl;
import org.am.domain.impl.GetCategoryUseCaseImpl;
import org.am.domain.impl.GetProductImageUseCaseImpl;
import org.am.domain.impl.GetProductUseCaseImpl;
import org.am.domain.impl.GetPurchaseUseCaseImpl;
import org.am.domain.impl.GetSupplierUseCaseImpl;
import org.am.domain.impl.GetWarehouseUseCaseImpl;
import org.am.domain.impl.UpdateProductUseCaseImpl;
import org.am.domain.impl.UpdatePurchaseUseCaseImpl;
import org.am.domain.impl.UpdateSupplierUseCaseImpl;
import org.am.domain.impl.UpdateWarehouseUseCaseImpl;
import org.am.domain.validation.validators.SupplierValidator;
import org.am.domain.validation.validators.WarehouseValidator;
import org.am.infrastructure.persistence.api.BrandDAO;
import org.am.infrastructure.persistence.api.CategoryDAO;
import org.am.infrastructure.persistence.api.ProductDAO;
import org.am.infrastructure.persistence.api.ProductImageDAO;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.api.SupplierDAO;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.am.infrastructure.persistence.converters.PurchaseEntityToPurchaseConverter;
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

    @Bean
    CreateProductUseCase createProductUseCase(ProductDAO productDAO) {

        return new CreateProductUseCaseImpl(productDAO);
    }

    @Bean
    UpdateProductUseCase updateProductUseCase(ProductDAO productDAO) {

        return new UpdateProductUseCaseImpl(productDAO);
    }

    @Bean
    GetProductUseCase getProductUseCase(ProductDAO productDAO) {

        return new GetProductUseCaseImpl(productDAO);
    }

    @Bean
    CreatePurchaseUseCase createPurchaseUseCase(PurchaseDAO purchaseDAO) {

        return new CreatePurchaseUseCaseImpl(purchaseDAO);
    }

    @Bean
    UpdatePurchaseUseCase updatePurchaseUseCase(PurchaseDAO purchaseDAO) {

        return new UpdatePurchaseUseCaseImpl(purchaseDAO);
    }

    @Bean
    GetPurchaseUseCase getPurchaseUseCase(PurchaseDAO purchaseDAO, PurchaseEntityToPurchaseConverter purchaseConverter) {

        return new GetPurchaseUseCaseImpl(purchaseDAO, purchaseConverter);
    }

    @Bean
    CreateBrandUseCase createBrandUseCase(BrandDAO brandDAO) {

        return new CreateBrandUseCaseImpl(brandDAO);
    }

    @Bean
    CreateCategoryUseCase createCategoryUseCase(CategoryDAO categoryDAO) {

        return new CreateCategoryUseCaseImpl(categoryDAO);
    }

    @Bean
    GetBrandUseCase getBrandUseCase(BrandDAO brandDAO) {

        return new GetBrandUseCaseImpl(brandDAO);
    }

    @Bean
    GetCategoryUseCase getCategoryUseCase(CategoryDAO categoryDAO) {

        return new GetCategoryUseCaseImpl(categoryDAO);
    }

    @Bean
    CreateProductImageUseCase createProductImageUseCase(ProductImageDAO productImageDAO) {

        return new CreateProductImageUseCaseImpl(productImageDAO);
    }

    @Bean
    GetProductImageUseCase getProductImageUseCase(ProductImageDAO productImageDAO) {

        return new GetProductImageUseCaseImpl(productImageDAO);
    }

    @Bean
    DeleteProductImageUseCase deleteProductImageUseCase(ProductImageDAO productImageDAO) {

        return new DeleteProductImageUseCaseImpl(productImageDAO);
    }
}
