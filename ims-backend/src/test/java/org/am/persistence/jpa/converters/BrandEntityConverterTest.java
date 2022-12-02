package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Brand;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.BrandEntityConverter;
import org.am.infrastructure.persistence.converters.BrandEntityConverterImpl;
import org.am.library.entities.BrandEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BrandEntityConverterTest {

    private final Faker faker = new Faker();

    private final BrandEntityConverter subject = new BrandEntityConverterImpl();

    @Test
    void convert_returnsABrandEntity() {

        // Given
        final Brand brand = faker.domain.brand().build();
        final BrandEntity expected = buildBrandEntity(brand);

        // When
        final BrandEntity brandEntity = subject.convert(brand);

        // Then
        assertThat(brandEntity).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private BrandEntity buildBrandEntity(Brand brand) {

        return BrandEntity.builder()
                .sid(brand.getSid())
                .name(brand.getName())
                .build();
    }
}
