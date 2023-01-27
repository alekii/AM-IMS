package org.am.rest.services.requests.converters;

import org.am.domain.catalog.Brand;
import org.am.fakers.Faker;
import org.am.rest.services.requests.BrandCreationRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BrandFromCreationRequestConverterTest {

    private final Faker faker = new Faker();

    private final BrandFromCreationRequestConverter brandCreationRequestConverter = new BrandFromCreationRequestConverter();

    @Test
    void convert_aBrandCreationRequest_toBrand() {

        // Given
        final BrandCreationRequest request = faker.domain.brandCreationRequest().build();

        // When
        final Brand brand = brandCreationRequestConverter.convert(request);

        // Then
        assertThat(brand.getName()).isEqualTo(request.getName());
    }
}
