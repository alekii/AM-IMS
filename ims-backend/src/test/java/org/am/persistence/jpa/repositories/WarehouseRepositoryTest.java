package org.am.persistence.jpa.repositories;

import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.library.entities.WarehouseEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    void testWarehouseSaveGeneratesId() {

        //Given
        WarehouseEntity warehouse = faker.entityFaker.warehouse().build();
        integrationTestPersister.save(warehouse);

        //When
        List<WarehouseEntity> warehouses = warehouseRepository.findAll();

        //Then
        assertThat(warehouses).isNotEmpty().hasSize(1);
        assertThat(warehouses.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingWarehouseWithNotUniqueSidThrowsException() {

        //Given
        WarehouseEntity warehouse = faker.entityFaker.warehouse().build();
        integrationTestPersister.save(warehouse);

        //When
        ThrowableAssert.ThrowingCallable saveWithSameSid = () -> integrationTestPersister.saveAndFlush(faker.entityFaker.warehouse()
                                                                                                               .sid(warehouse.getSid())
                                                                                                               .build());

        //Then
        assertThatThrownBy(saveWithSameSid)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingWarehouseWithNotUniqueNameThrowsException() {

        //Given
        WarehouseEntity warehouse = faker.entityFaker.warehouse().build();
        integrationTestPersister.save(warehouse);

        //When
        ThrowableAssert.ThrowingCallable saveWithSameSid = () -> integrationTestPersister.saveAndFlush(faker.entityFaker.warehouse()
                                                                                                               .name(warehouse.getName())
                                                                                                               .build());

        //Then
        assertThatThrownBy(saveWithSameSid)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }
}
