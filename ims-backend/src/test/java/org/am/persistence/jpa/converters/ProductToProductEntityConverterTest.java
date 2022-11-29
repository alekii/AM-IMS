package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Product;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.ProductToProductEntityConverter;
import org.am.infrastructure.persistence.converters.ProductToProductEntityConverterImpl;
import org.am.infrastructure.persistence.converters.SupplierToSupplierEntityConverter;
import org.am.infrastructure.persistence.converters.SupplierToSupplierEntityConverterImpl;
import org.am.library.entities.BrandEntity;
import org.am.library.entities.CategoryEntity;
import org.am.library.entities.ProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductToProductEntityConverterTest {

    private final Faker faker = new Faker();

    private final SupplierToSupplierEntityConverter supplierEntityConverter = new SupplierToSupplierEntityConverterImpl();

    private final ProductToProductEntityConverter subject = new ProductToProductEntityConverterImpl();

    @Test
    void convert_returnsAProductEntity() {

        // Given
        final Product product = faker.domain.product().build();

        final ProductEntity productEntity = buildProductEntity(product);

        // When
        final ProductEntity convertedProductEntity = subject.convert(product);

        // Then
        Assertions.assertThat(convertedProductEntity.getSid()).isInstanceOf(UUID.class);
        assertThat(convertedProductEntity)
                .usingRecursiveComparison()
                .ignoringFields("sid")
                .isEqualTo(productEntity);
    }

    private ProductEntity buildProductEntity(Product product) {

        return ProductEntity.builder()
                .sid(product.getSid())
                .name(product.getName())
                .quantity(product.getQuantity())
                .discount(product.getDiscount())
                .price(product.getPrice())
                .description(product.getDescription())
                .brand(BrandEntity.builder()
                               .name(product.getBrand().getName())
                               .sid(product.getBrand().getSid())
                               .build())
                .category(CategoryEntity.builder()
                                  .sid(product.getCategory().getSid())
                                  .name(product.getCategory().getName())
                                  .build())
                .supplied_by(supplierEntityConverter.convert(product.getSupplier()))
                .warehouseSid(product.getWarehouseSid())
                .date_received(product.getDateReceived())
                .sku(product.getSku())
                .build();
    }
}
