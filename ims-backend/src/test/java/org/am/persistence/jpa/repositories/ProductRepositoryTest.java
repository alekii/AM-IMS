package org.am.persistence.jpa.repositories;

import org.am.infrastructure.products.ProductRepository;
import org.am.library.entities.ProductEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ProductRepository subject;

    @Test
    void testProductSaveGeneratesId() {

        // Given
        ProductEntity productEntity = faker.entity.product().build();
        integrationTestPersister.save(productEntity);

        // When
        List<ProductEntity> products = subject.findAll();

        // Then
        assertThat(products).isNotEmpty().hasSize(1);
        assertThat(products.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingProductWithNotUniqueSidThrowsException() {

        // Given
        ProductEntity productEntity = faker.entity.product().build();
        integrationTestPersister.save(productEntity);

        // When
        ThrowableAssert.ThrowingCallable saveWithSameUUID = () -> integrationTestPersister.saveAndFlush(faker.entity.product()
                                                                                                                .sid(productEntity.getSid())
                                                                                                                .build());

        // Then
        assertThatThrownBy(saveWithSameUUID)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingProductWithNotUniqueNameThrowsException() {

        // Given
        ProductEntity productEntity = faker.entity.product().build();
        integrationTestPersister.save(productEntity);

        // When
        ThrowableAssert.ThrowingCallable saveWithSameUUID = () -> integrationTestPersister.saveAndFlush(faker.entity.product()
                                                                                                                .name(productEntity.getName())
                                                                                                                .build());

        // Then
        assertThatThrownBy(saveWithSameUUID)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }
}
