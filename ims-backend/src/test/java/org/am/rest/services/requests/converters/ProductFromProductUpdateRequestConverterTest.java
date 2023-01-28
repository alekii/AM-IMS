package org.am.rest.services.requests.converters;

import com.github.javafaker.Faker;
import org.am.domain.catalog.Brand;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.Product;
import org.am.rest.services.requests.ProductUpdateRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductFromProductUpdateRequestConverterTest {

    private final Faker faker = new Faker();

    private final ProductFromProductUpdateRequestConverter subject = new ProductFromProductUpdateRequestConverter();

    @Test
    public void convert_returnsProduct_FromProductUpdateRequest() {

        // Given
        final ProductUpdateRequest productUpdateRequest = buildProductUpdateRequest();
        final Product product = buildProduct(productUpdateRequest);

        // When
        final Product createdProduct = subject.convert(productUpdateRequest);

        // Then
        assertThat(createdProduct)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    private Product buildProduct(ProductUpdateRequest productUpdateRequest) {

        return Product.builder()
                .name(productUpdateRequest.getName())
                .price(productUpdateRequest.getPrice())
                .discount(productUpdateRequest.getDiscount())
                .quantity(productUpdateRequest.getQuantity())
                .sid(productUpdateRequest.getSid())
                .sku(productUpdateRequest.getSku())
                .brand(Brand.builder()
                               .sid(productUpdateRequest.getBrand().getSid())
                               .name(productUpdateRequest.getBrand().getName())
                               .build())
                .category(Category.builder()
                                  .name(productUpdateRequest.getCategory().getName())
                                  .sid(productUpdateRequest.getCategory().getSid())
                                  .build())
                .build();
    }

    private ProductUpdateRequest buildProductUpdateRequest() {

        return ProductUpdateRequest.builder()
                .name(faker.funnyName().name())
                .sid(UUID.randomUUID())
                .price(faker.number().randomDouble(2, 1, 99999))
                .sku(faker.funnyName().name())
                .category(ProductUpdateRequest.CategoryRequest.builder()
                                  .sid(UUID.randomUUID())
                                  .name(faker.name().name())
                                  .build())
                .brand(ProductUpdateRequest.BrandRequest.builder()
                               .sid(UUID.randomUUID())
                               .name(faker.book().author())
                               .build())
                .quantity(faker.number().numberBetween(1, 99999))
                .discount(faker.number().randomDouble(2, 10, 60))
                .build();
    }
}

