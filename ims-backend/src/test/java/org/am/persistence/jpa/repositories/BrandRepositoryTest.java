package org.am.persistence.jpa.repositories;

import org.am.infrastructure.brand.BrandRepository;
import org.am.library.entities.BrandEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BrandRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private BrandRepository subject;

    @Test
    void testBrandsSaveGeneratesId() {
        //Given
        BrandEntity brandEntity = faker.entity.brand().build();
        integrationTestPersister.save(brandEntity);

        //When
        List<BrandEntity> brands = subject.findAll();

        //Then
        assertThat(brands).isNotEmpty().hasSize(1);
        assertThat(brands.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingProductImageWithNotUniqueSidThrowsException() {

        // Given
        BrandEntity brandEntity = faker.entity.brand().build();
        integrationTestPersister.save(brandEntity);

        // When
        ThrowableAssert.ThrowingCallable saveWithSameUUID = () -> integrationTestPersister.saveAndFlush(faker.entity.brand()
                                                                                                                .sid(brandEntity.getSid())
                                                                                                                .build());

        // Then
        assertThatThrownBy(saveWithSameUUID)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingBrandNotUniqueNameThrowsException() {

        // Given
        BrandEntity brandEntity = faker.entity.brand().build();
        integrationTestPersister.save(brandEntity);

        // When
        ThrowableAssert.ThrowingCallable saveWithSameName = () -> integrationTestPersister.saveAndFlush(faker.entity.brand()
                                                                                                                .name(brandEntity.getName())
                                                                                                                .build());

        // Then
        assertThatThrownBy(saveWithSameName)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }
}
