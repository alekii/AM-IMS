package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.ProductImage;
import org.am.domain.catalog.exceptions.NotFound.ProductNotFoundException;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.ProductImageDAO;
import org.am.infrastructure.productimages.ImagesRepository;
import org.am.infrastructure.products.ProductRepository;
import org.am.library.entities.ImageEntity;
import org.am.library.entities.ProductEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductImageDAOImplTest extends BaseIntegrationTest {

    private final Faker faker = new Faker();

    @Autowired
    private ProductImageDAO subject;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Test
    void persist_whenProductExists_persistImage() {

        // Given
        final ProductEntity productEntity = integrationTestPersister.save(faker.entity.product().build());

        final ProductImage image = faker.domain.productImage()
                .productSid(productEntity.getSid())
                .build();

        // When
        final ProductImage result = subject.persist(image);

        // Then
        final ImageEntity expected = ImageEntity.builder()
                .sid(image.getSid())
                .product(productEntity)
                .imagePath(image.getImagePath())
                .build();

        final List<ImageEntity> images = imagesRepository.findAll();
        assertThat(images).extracting(ImageEntity::getSid).containsExactly(expected.getSid());

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(image);
    }

    @Test
    void persist_whenProductDoesNotExist_throwsProductNotFoundException() {

        // Given
        final ProductImage image = faker.domain.productImage().build();

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.persist(image);

        // Then
        assertThatThrownBy(callable).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void findByProductSid_whenImagesExist_returnsListOfImages() {

        // Given
        final ProductEntity productEntity = integrationTestPersister.save(faker.entity.product().build());

        final ImageEntity imageEntity1 = integrationTestPersister.save(faker.entity.productImage()
                                                                               .product(productEntity)
                                                                               .build());
        final ImageEntity imageEntity2 = integrationTestPersister.save(faker.entity.productImage()
                                                                               .product(productEntity)
                                                                               .build());

        // When
        final List<ProductImage> result = subject.findByProductSid(productEntity.getSid());

        // Then
        assertThat(result)
                .hasSize(2)
                .extracting(ProductImage::getSid)
                .containsExactly(imageEntity1.getSid(), imageEntity2.getSid());
    }

    @Test
    void delete_whenCalled_deletesImage() {

        final ImageEntity image = integrationTestPersister.save(faker.entity.productImage().build());

        //When
        subject.delete(image.getId());

        //Then
        assertThat(imagesRepository.findAll())
                .isEmpty();
    }
}
