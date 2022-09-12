package org.am.persistence.jpa.repositories;

import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.entities.WarehouseEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    private WarehouseEntity warehouse;

    @BeforeEach
    void init() {

        warehouse = faker.entity.warehouse().build();
        integrationTestPersister.save(warehouse);
    }

    @Test
    void testWarehouseSaveGeneratesId() {

        //When
        List<WarehouseEntity> warehouses = warehouseRepository.findAll();

        //Then
        assertThat(warehouses).isNotEmpty().hasSize(1);
        assertThat(warehouses.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingWarehouseWithNotUniqueSidThrowsException() {

        //When
        ThrowableAssert.ThrowingCallable saveWithSameSid = () -> integrationTestPersister.saveAndFlush(faker.entity.warehouse()
                                                                                                               .sid(warehouse.getSid())
                                                                                                               .build());

        //Then
        assertThatThrownBy(saveWithSameSid)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingWarehouseWithNotUniqueNameThrowsException() {

        //When
        ThrowableAssert.ThrowingCallable saveWithSameSid = () -> integrationTestPersister.saveAndFlush(faker.entity.warehouse()
                                                                                                               .name(warehouse.getName())
                                                                                                               .build());

        //Then
        assertThatThrownBy(saveWithSameSid)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testFetchByID_returnsWarehouseProjection() {

        //When
        WarehouseProjection warehouseProjection = warehouseRepository.findByIdFetch(1);

        //Then
        assertThat(warehouseProjection.getSid().equals(warehouse.getSid()));
    }
}
