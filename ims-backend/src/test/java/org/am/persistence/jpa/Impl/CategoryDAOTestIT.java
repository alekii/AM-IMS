package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.Category;
import org.am.domain.catalog.exceptions.conflicts.CategoryAlreadyExistsException;
import org.am.infrastructure.category.CategoryRepository;
import org.am.infrastructure.persistence.impl.CategoryDAOImpl;
import org.am.library.entities.CategoryEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryDAOTestIT extends BaseIntegrationTest {

    @Autowired
    private CategoryDAOImpl subject;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void create_whenBrandDoesNotExist_returnsPersistedBrand() {

        // Given
        final CategoryEntity categoryEntity = faker.entity.category().build();
        final Category expected = buildCategory(categoryEntity);

        // When
        final Category category = subject.create(expected);

        // Then
        assertThat(category.getSid()).isEqualTo(expected.getSid());

        final Optional<CategoryEntity> persistedBrand = categoryRepository.findBySid(categoryEntity.getSid());
        assertThat(persistedBrand).isPresent();
    }

    @Test
    void create_whenBrandExists_throwsBrandAlreadyExistsException() {

        // Given
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final Category category = buildCategory(categoryEntity);

        // When
        final ThrowableAssert.ThrowingCallable create = () -> subject.create(category);

        // Then
        assertThatThrownBy(create).isInstanceOf(CategoryAlreadyExistsException.class);
    }

    @Test
    void findBySid_whenCategoryExists_returnsCategoryEntity() {

        //Given
        final CategoryEntity entity = faker.entity.category().build();
        integrationTestPersister.save(entity);

        //When
        final Category result = subject.findBySid(entity.getSid());

        //Then
        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findAll_whenBrandsExist_returnsBrands() {

        // Given
        final Category category1 = faker.domain.category().build();
        final Category category2 = faker.domain.category().build();

        final CategoryEntity entity1 = faker.entity.category().sid(category1.getSid()).build();
        final CategoryEntity entity2 = faker.entity.category().sid(category2.getSid()).build();

        integrationTestPersister.save(entity1);
        integrationTestPersister.save(entity2);

        // When
        final List<Category> result = subject.findAll();

        // Then
        assertThat(result)
                .extracting(Category::getSid)
                .containsExactlyInAnyOrder(category1.getSid(), category2.getSid());
    }

    private Category buildCategory(CategoryEntity entity) {

        return Category.builder()
                .sid(entity.getSid())
                .name(entity.getName())
                .build();
    }
}
