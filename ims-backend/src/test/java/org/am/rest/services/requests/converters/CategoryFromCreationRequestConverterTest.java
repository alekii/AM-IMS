package org.am.rest.services.requests.converters;

import org.am.domain.catalog.Category;
import org.am.fakers.Faker;
import org.am.rest.services.requests.CategoryCreationRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryFromCreationRequestConverterTest {

    private final Faker faker = new Faker();

    private final CategoryFromCreationRequestConverter categoryCreationRequestConverter = new CategoryFromCreationRequestConverter();

    @Test
    void convert_aCategoryCreationRequest_toCategory() {

        // Given
        final CategoryCreationRequest request = faker.domain.categoryCreationRequest().build();

        // When
        final Category category = categoryCreationRequestConverter.convert(request);

        // Then
        assertThat(category.getName()).isEqualTo(request.getName());
    }
}
