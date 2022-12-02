package org.am.persistence.jpa.repositories;

import org.am.infrastructure.category.CategoryRepository;
import org.am.library.entities.CategoryEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private CategoryRepository subject;

    @Test
    void testBrandsSaveGeneratesId() {
        //Given
        CategoryEntity categoryEntity = faker.entity.category().build();
        integrationTestPersister.save(categoryEntity);

        //When
        List<CategoryEntity> categories = subject.findAll();

        //Then
        assertThat(categories).isNotEmpty().hasSize(1);
        assertThat(categories.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingCategoryWithNotUniqueSidThrowsException() {

        // Given
        CategoryEntity categoryEntity = faker.entity.category().build();
        integrationTestPersister.save(categoryEntity);

        // When
        ThrowableAssert.ThrowingCallable saveWithSameUUID = () -> integrationTestPersister.saveAndFlush(faker.entity.category()
                                                                                                                .sid(categoryEntity.getSid())
                                                                                                                .build());

        // Then
        assertThatThrownBy(saveWithSameUUID)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingCategoryNotUniqueNameThrowsException() {

        // Given
        CategoryEntity categoryEntity = faker.entity.category().build();
        integrationTestPersister.save(categoryEntity);

        // When
        ThrowableAssert.ThrowingCallable saveWithSameName = () -> integrationTestPersister.saveAndFlush(faker.entity.category()
                                                                                                                .name(categoryEntity.getName())
                                                                                                                .build());

        // Then
        assertThatThrownBy(saveWithSameName)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }
}


