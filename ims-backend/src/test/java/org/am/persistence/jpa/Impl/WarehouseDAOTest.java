package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.Warehouse;
import org.am.infrastructure.persistence.impl.WarehouseDAOImpl;
import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.library.entities.WarehouseEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseDAOTest extends BaseIntegrationTest {

    @Autowired
    private WarehouseDAOImpl subject;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    void findBySid_whenWarehouseExists_returnsWarehouseEntity() {

        //Given
        final WarehouseEntity entity = faker.entity.warehouse().build();

        integrationTestPersister.save(entity);

        //When
        final Optional<Warehouse> result = subject.findBySid(entity.getSid());

        //Then
        assertThat(result).isNotEmpty();
        assertThat(result.get()).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    void findById_whenWarehousesExist_returnsWarehouses() {

        // Given
        final Warehouse warehouse1 = faker.domain.warehouse().build();
        final Warehouse warehouse2 = faker.domain.warehouse().build();

        final WarehouseEntity entity1 = faker.entity.warehouse().sid(warehouse1.getSid()).build();
        final WarehouseEntity entity2 = faker.entity.warehouse().sid(warehouse2.getSid()).build();

        integrationTestPersister.save(entity1);
        integrationTestPersister.save(entity2);

        // When
        final List<Warehouse> result = subject.findAll();

        // Then
        assertThat(result)
                .extracting(Warehouse::getSid)
                .containsExactlyInAnyOrder(warehouse1.getSid(), warehouse2.getSid());
    }
}
