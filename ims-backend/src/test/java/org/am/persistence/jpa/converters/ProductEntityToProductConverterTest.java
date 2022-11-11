package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Brand;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.Product;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.ProductEntityToProductConverter;
import org.am.infrastructure.persistence.converters.ProductEntityToProductConverterImpl;
import org.am.infrastructure.persistence.converters.SupplierConverter;
import org.am.infrastructure.persistence.converters.SupplierConverterImpl;
import org.am.library.entities.ProductEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductEntityToProductConverterTest {

    private final Faker faker = new Faker();

    private final SupplierConverter supplierConverter = new SupplierConverterImpl();

    private final ProductEntityToProductConverter subject = new ProductEntityToProductConverterImpl();

    @Test
    void convert_returnsAProduct() {

        // Given
        final ProductEntity entity = faker.entity.product().build();

        final Product expected = buildProduct(entity);

        // When
        final Product product = subject.convert(entity);

        assertThat(product).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private Product buildProduct(ProductEntity entity) {

        return Product.builder()
                .sid(entity.getSid())
                .price(entity.getPrice())
                .name(entity.getName())
                .discount(entity.getDiscount())
                .receivedBy(entity.getReceived_by())
                .dateReceived(entity.getDate_received())
                .sku(entity.getSku())
                .description(entity.getDescription())
                .brand(Brand.builder()
                               .name(entity.getBrand().getName())
                               .sid(entity.getBrand().getSid())
                               .build())
                .category(Category.builder()
                                  .sid(entity.getCategory().getSid())
                                  .name(entity.getCategory().getName())
                                  .build())
                .quantity(entity.getQuantity())
                .supplier(supplierConverter.convert(entity.getSupplied_by()))
                .warehouseSid(entity.getWarehouseSid())
                .build();
    }
}
