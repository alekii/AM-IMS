package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Category;
import org.am.fakers.Faker;
import org.am.rest.services.responses.CategoryResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryResponseConverterTest {

    private final Faker faker = new Faker();

    private final CategoryResponseConverter CategoryResponseConverter = new CategoryResponseConverter();

    @Test
    void convert_aCategory_toCategoryResponse() {

        // Given
        final Category category = faker.domain.category().build();

        // When
        final CategoryResponse categoryResponse = CategoryResponseConverter.convert(category);

        // Then
        assertThat(categoryResponse)
                .isEqualTo(CategoryResponse.builder()
                                   .sid(category.getSid())
                                   .name(category.getName())
                                   .build());
    }
}
