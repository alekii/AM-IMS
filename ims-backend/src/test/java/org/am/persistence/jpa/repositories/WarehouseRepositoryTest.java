package org.am.persistence.jpa.repositories;

import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.library.entities.WarehouseEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    void testWarehouseSaveGeneratesId() {

        //Given
        WarehouseEntity warehouse = faker.entityFaker.buildWarehousewithCoverages().build();
        integrationTestPersister.save(warehouse);

        //When
        List<WarehouseEntity> warehouses = warehouseRepository.findAll();

        //Then
        assertThat(warehouses).isNotEmpty().hasSize(1);
        assertThat(warehouses.get(0).getId()).isNotNull();
    }
}
