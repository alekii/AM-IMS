package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Brand;
import org.am.fakers.Faker;
import org.am.rest.services.responses.BrandResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BrandResponseConverterTest {

    private final Faker faker = new Faker();

    private final BrandResponseConverter brandResponseConverter = new BrandResponseConverter();

    @Test
    void convert_aBrand_toBrandResponse() {

        // Given
        final Brand brand = faker.domain.brand().build();

        // When
        final BrandResponse brandResponse = brandResponseConverter.convert(brand);

        // Then
        assertThat(brandResponse)
                .isEqualTo(BrandResponse.builder()
                                   .sid(brand.getSid())
                                   .name(brand.getName())
                                   .build());
    }
}
