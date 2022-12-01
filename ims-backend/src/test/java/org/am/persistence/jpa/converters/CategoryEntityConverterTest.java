package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Category;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.CategoryEntityConverter;
import org.am.infrastructure.persistence.converters.CategoryEntityConverterImpl;
import org.am.library.entities.CategoryEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CategoryEntityConverterTest {

    private final Faker faker = new Faker();

    private final CategoryEntityConverter subject = new CategoryEntityConverterImpl();

    @Test
    void convert_returnsACategoryEntity() {

        final Category category = faker.domain.category().build();
        final CategoryEntity expected = buildCategoryEntity(category);

        // When
        final CategoryEntity categoryEntity = subject.convert(category);

        // Then
        assertThat(categoryEntity).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private CategoryEntity buildCategoryEntity(Category category) {

        return CategoryEntity.builder()
                .sid(category.getSid())
                .name(category.getName())
                .build();
    }
}
