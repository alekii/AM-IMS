package org.am.persistence.jpa.repositories;

import org.am.fakers.EntityFaker;
import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.library.entities.Warehouse;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    private EntityFaker entityFaker;

    @Test
    void testWarehouseSaveGeneratesId() {

        //Given
        Warehouse warehouse = entityFaker.warehouse().build();
        integrationTestPersister.save(warehouse);

        //When
        List<Warehouse> warehouses = warehouseRepository.findAll();

        //Then
        assertThat(warehouses).isNotEmpty().hasSize(1);
        assertThat(warehouses.get(0).getId()).isNotNull();
    }
}
