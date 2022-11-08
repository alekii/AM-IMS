package org.am.persistence.jpa.repositories;

import org.am.infrastructure.products.ProductRepository;
import org.am.library.entities.ProductEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ProductRepository subject;

    @Test
    void testProductSaveGeneratesId() {

        //Given
        ProductEntity productEntity = faker.entity.product().build();
        integrationTestPersister.save(productEntity);

        //When
        List<ProductEntity> products = subject.findAll();

        //Then
        assertThat(products).isNotEmpty().hasSize(1);
        assertThat(products.get(0).getId()).isNotNull();
    }
}
