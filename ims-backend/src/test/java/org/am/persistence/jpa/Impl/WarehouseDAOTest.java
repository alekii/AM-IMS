package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.conflicts.TownNotExistException;
import org.am.domain.catalog.exceptions.conflicts.WarehouseAlreadyExistsException;
import org.am.infrastructure.Address.AddressRepository;
import org.am.infrastructure.persistence.impl.WarehouseDAOImpl;
import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.WarehouseEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseDAOTest extends BaseIntegrationTest {

    @Autowired
    private WarehouseDAOImpl subject;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void create_whenWarehouseDoesNotExist_returnPersistedWarehouse() {

        // Given
        final WarehouseEntity warehouseEntity = faker.entity.warehouse().build();
        final AddressEntity addressEntity = integrationTestPersister.save(faker.entity.address().build());
        final Warehouse warehouse = buildWarehouse(warehouseEntity, addressEntity);

        // When
        final Warehouse warehouse1 = subject.create(warehouse);

        // Then
        assertThat(warehouse1.getSid()).isEqualTo(warehouse.getSid());

        final Optional<WarehouseEntity> createdWarehouse = warehouseRepository.findBySid(warehouseEntity.getSid());
        assertThat(createdWarehouse).isPresent();
    }

    @Test
    void create_whenWarehouseExists_throwsWarehouseAlreadyExistsException() {

        // Given
        final WarehouseEntity warehouseEntity = integrationTestPersister.save(faker.entity.warehouse().build());
        final AddressEntity addressEntity = integrationTestPersister.save(faker.entity.address().build());
        final Warehouse warehouse = buildWarehouse(warehouseEntity, addressEntity);

        // When
        final ThrowableAssert.ThrowingCallable create = () -> subject.create(warehouse);

        // Then
        assertThatThrownBy(create).isInstanceOf(WarehouseAlreadyExistsException.class);
    }

    @Test
    void create_whenWarehouseExists_AntTownDoesNotExists_throwsTownNotExistException() {

        // Given
        final WarehouseEntity warehouseEntity = integrationTestPersister.save(faker.entity.warehouse().build());
        final AddressEntity addressEntity = integrationTestPersister.save(faker.entity.address().build());
        final Warehouse warehouse = buildWarehouseWithInvalidTown(warehouseEntity, addressEntity);

        // When
        final ThrowableAssert.ThrowingCallable create = () -> subject.create(warehouse);

        // Then
        assertThatThrownBy(create).isInstanceOf(TownNotExistException.class);
    }

    @Test
    void findBySid_whenWarehouseExists_returnsWarehouseEntity() {

        //Given
        final WarehouseEntity entity = faker.entity.warehouse().build();

        integrationTestPersister.save(entity);

        //When
        final Optional<Warehouse> result = subject.findBySid(entity.getSid());

        //Then
        assertThat(result).isNotEmpty();
        assertThat(result.get())
                .usingRecursiveComparison()
                .ignoringFields("address.county")
                .isEqualTo(entity);

        assertThat(result.get().getAddress().getCounty())
                .usingRecursiveComparison()
                .isEqualTo(entity.getAddress().getTown().getCounty());
    }

    @Test
    void findAll_whenWarehousesExist_returnsWarehouses() {

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

    private Warehouse buildWarehouse(final WarehouseEntity warehouseEntity, final AddressEntity addressEntity) {

        return faker.domain.warehouse()
                .sid(warehouseEntity.getSid())
                .name(warehouseEntity.getName())
                .address(Address.builder()
                                 .town(Town.builder()
                                               .sid(addressEntity.getTown().getSid())
                                               .name(addressEntity.getTown().getName())
                                               .build())
                                 .county(County.builder()
                                                 .sid(addressEntity.getTown().getCounty().getSid())
                                                 .name(addressEntity.getTown().getCounty().getName())
                                                 .build())
                                 .street(addressEntity.getStreet())
                                 .build())
                .build();
    }

    private Warehouse buildWarehouseWithInvalidTown(final WarehouseEntity warehouseEntity, final AddressEntity addressEntity) {

        return faker.domain.warehouse()
                .sid(warehouseEntity.getSid())
                .name(faker.name().fullName())
                .address(Address.builder()
                                 .town(Town.builder()
                                               .sid(UUID.randomUUID())
                                               .name(addressEntity.getTown().getName())
                                               .build())
                                 .county(County.builder()
                                                 .sid(addressEntity.getTown().getCounty().getSid())
                                                 .name(addressEntity.getTown().getCounty().getName())
                                                 .build())
                                 .street(addressEntity.getStreet())
                                 .build())
                .build();
    }
}
