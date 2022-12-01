package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Category;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.CategoryConverter;
import org.am.infrastructure.persistence.converters.CategoryConverterImpl;
import org.am.library.entities.CategoryEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CategoryConverterTest {

    private final Faker faker = new Faker();

    private final CategoryConverter subject = new CategoryConverterImpl();

    @Test
    void convert_returnsACategory() {

        // Given
        final CategoryEntity entity = faker.entity.category().build();

        final Category expected = buildCategory(entity);

        // When
        final Category category = subject.convert(entity);

        // Then
        assertThat(category).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private Category buildCategory(CategoryEntity entity) {

        return Category.builder()
                .sid(entity.getSid())
                .name(entity.getName())
                .build();
    }
}
