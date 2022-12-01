package org.am.persistence.jpa.converters;

import org.am.domain.catalog.ProductImage;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.ProductImageEntityConverter;
import org.am.infrastructure.persistence.converters.ProductImageEntityConverterImpl;
import org.am.library.entities.ImageEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductImageEntityConverterTest {

    private final Faker faker = new Faker();

    private final ProductImageEntityConverter subject = new ProductImageEntityConverterImpl();

    @Test
    void convert_returnsAProductImageEntity() {

        // Given
        final ProductImage productImage = faker.domain.productImage().build();

        // When
        final ImageEntity imageEntity = subject.convert(productImage);

        // Then
        assertThat(imageEntity)
                .usingRecursiveComparison()
                .isEqualTo(buildProductImageEntity(productImage));
    }

    private ImageEntity buildProductImageEntity(ProductImage productImage) {

        return ImageEntity.builder()
                .sid(productImage.getSid())
                .imagePath(productImage.getImagePath())
                .build();
    }
}
