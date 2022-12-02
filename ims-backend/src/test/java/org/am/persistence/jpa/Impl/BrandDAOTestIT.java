package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.Brand;
import org.am.domain.catalog.exceptions.conflicts.BrandAlreadyExistsException;
import org.am.infrastructure.brand.BrandRepository;
import org.am.infrastructure.persistence.impl.BrandDAOImpl;
import org.am.library.entities.BrandEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BrandDAOTestIT extends BaseIntegrationTest {

    @Autowired
    private BrandDAOImpl subject;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void create_whenBrandDoesNotExist_returnsPersistedBrand() {

        // Given
        final BrandEntity brandEntity = faker.entity.brand().build();
        final Brand expected = buildBrand(brandEntity);

        // When
        final Brand brand = subject.create(expected);

        // Then
        assertThat(brand.getSid()).isEqualTo(expected.getSid());

        final Optional<BrandEntity> persistedBrand = brandRepository.findBySid(brandEntity.getSid());
        assertThat(persistedBrand).isPresent();
    }

    @Test
    void create_whenBrandExists_throwsBrandAlreadyExistsException() {

        // Given
        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final Brand brand = buildBrand(brandEntity);

        // When
        final ThrowableAssert.ThrowingCallable create = () -> subject.create(brand);

        // Then
        assertThatThrownBy(create).isInstanceOf(BrandAlreadyExistsException.class);
    }

    @Test
    void findBySid_whenBrandExists_returnsBrandEntity() {

        //Given
        final BrandEntity entity = faker.entity.brand().build();
        integrationTestPersister.save(entity);

        //When
        final Brand result = subject.findBySid(entity.getSid());

        //Then
        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findAll_whenBrandsExist_returnsBrands() {

        // Given
        final Brand brand1 = faker.domain.brand().build();
        final Brand brand2 = faker.domain.brand().build();

        final BrandEntity entity1 = faker.entity.brand().sid(brand1.getSid()).build();
        final BrandEntity entity2 = faker.entity.brand().sid(brand2.getSid()).build();

        integrationTestPersister.save(entity1);
        integrationTestPersister.save(entity2);

        // When
        final List<Brand> result = subject.findAll();

        // Then
        assertThat(result)
                .extracting(Brand::getSid)
                .containsExactlyInAnyOrder(brand1.getSid(), brand2.getSid());
    }

    private Brand buildBrand(BrandEntity entity) {

        return Brand.builder()
                .sid(entity.getSid())
                .name(entity.getName())
                .build();
    }
}
