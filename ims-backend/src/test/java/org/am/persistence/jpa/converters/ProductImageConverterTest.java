package org.am.persistence.jpa.converters;

import org.am.domain.catalog.ProductImage;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.ProductImageConverter;
import org.am.infrastructure.persistence.converters.ProductImageConverterImpl;
import org.am.library.entities.ImageEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductImageConverterTest {

    private final Faker faker = new Faker();

    private final ProductImageConverter subject = new ProductImageConverterImpl();

    @Test
    void convert_returnsAProductImage() {

        // Given
        final ImageEntity imageEntity = faker.entity.productImage().build();
        final ProductImage expected = buildProductImage(imageEntity);

        // When
        final ProductImage productImage = subject.convert(imageEntity);

        // Then
        assertThat(productImage).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private ProductImage buildProductImage(ImageEntity imageEntity) {

        return ProductImage.builder()
                .sid(imageEntity.getSid())
                .imagePath(imageEntity.getImagePath())
                .productSid(imageEntity.getProduct().getSid())
                .build();
    }
}
