package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Product;
import org.am.fakers.Faker;
import org.am.rest.services.responses.ProductMinimumResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductToMinimumResponseConverterTest {

    private final Faker faker = new Faker();

    private final ProductToMinimumResponseConverter productToMinimumResponseConverter
            = new ProductToMinimumResponseConverter();

    @Test
    void convert_Warehouse_returnsProductToProductMinimumResponse() {

        // Given
        final Product product = faker.domain.product().build();
        final ProductMinimumResponse productMinimumResponse = buildProductMinimumResponse(product);

        // When
        final ProductMinimumResponse response = productToMinimumResponseConverter.convert(product);

        // Then
        assertThat(productMinimumResponse).usingRecursiveComparison().isEqualTo(response);
    }

    private ProductMinimumResponse buildProductMinimumResponse(Product product) {

        return ProductMinimumResponse.builder()
                .sid(product.getSid())
                .name(product.getName())
                .discount(product.getDiscount())
                .price(product.getPrice())
                .build();
    }
}
