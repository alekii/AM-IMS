package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Brand;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.BrandConverter;
import org.am.infrastructure.persistence.converters.BrandConverterImpl;
import org.am.library.entities.BrandEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BrandConverterTest {

    private final Faker faker = new Faker();

    private final BrandConverter subject = new BrandConverterImpl();

    @Test
    void convert_returnsABrand() {

        // Given
        final BrandEntity entity = faker.entity.brand().build();

        final Brand expected = buildBrand(entity);

        // When
        final Brand brand = subject.convert(entity);

        // Then
        assertThat(brand).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private Brand buildBrand(BrandEntity entity) {

        return Brand.builder()
                .sid(entity.getSid())
                .name(entity.getName())
                .build();
    }
}
