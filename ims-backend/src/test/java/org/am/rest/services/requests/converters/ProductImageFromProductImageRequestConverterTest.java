package org.am.rest.services.requests.converters;

import com.github.javafaker.Faker;
import org.am.domain.catalog.ProductImage;
import org.am.rest.services.requests.ProductImageCreateRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductImageFromProductImageRequestConverterTest {

    private final Faker faker = new Faker();

    private final ProductImageFromProductImageRequestConverter subject = new ProductImageFromProductImageRequestConverter();

    @Test
    public void convert_returnsProduct_FromProductCreateRequest() {

        // Given
        final ProductImageCreateRequest productImageCreateRequest = buildProductImageCreateRequest();
        final ProductImage productImage = buildProductImage(productImageCreateRequest);

        // When
        final ProductImage createdProductImage = subject.convert(productImageCreateRequest);

        // Then
        assertThat(createdProductImage)
                .usingRecursiveComparison()
                .ignoringFields("sid")
                .isEqualTo(productImage);
    }

    private ProductImage buildProductImage(ProductImageCreateRequest productImageCreateRequest) {

        return ProductImage.builder()
                .productSid(productImageCreateRequest.getProductSid())
                .imagePath(productImageCreateRequest.getImagePath())
                .build();
    }

    private ProductImageCreateRequest buildProductImageCreateRequest() {

        return ProductImageCreateRequest.builder()
                .imagePath(faker.internet().url())
                .productSid(UUID.randomUUID())
                .build();
    }
}
