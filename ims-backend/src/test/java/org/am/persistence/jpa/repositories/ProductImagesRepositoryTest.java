package org.am.persistence.jpa.repositories;

import org.am.infrastructure.productimages.ImagesRepository;
import org.am.library.entities.ImageEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductImagesRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ImagesRepository subject;

    @Test
    void testProductImagesSaveGeneratesId() {

        //Given
        ImageEntity imageEntity = faker.entity.productImage().build();
        integrationTestPersister.save(imageEntity);

        //When
        List<ImageEntity> images = subject.findAll();

        //Then
        assertThat(images).isNotEmpty().hasSize(1);
        assertThat(images.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingProductImageWithNotUniqueSidThrowsException() {

        // Given
        ImageEntity imageEntity = faker.entity.productImage().build();
        integrationTestPersister.save(imageEntity);

        // When
        ThrowableAssert.ThrowingCallable saveWithSameUUID = () -> integrationTestPersister.saveAndFlush(faker.entity.productImage()
                                                                                                                .sid(imageEntity.getSid())
                                                                                                                .build());

        // Then
        assertThatThrownBy(saveWithSameUUID)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }
}
