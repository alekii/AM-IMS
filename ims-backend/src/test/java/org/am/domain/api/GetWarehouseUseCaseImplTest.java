package org.am.domain.api;

import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.WarehouseNotFoundException;
import org.am.domain.impl.GetWarehouseUseCaseImpl;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GetWarehouseUseCaseImplTest {

    private final Faker faker = new Faker();

    private final Supplier<Warehouse> sWarehouse = () -> faker.domain.warehouse().build();

    @Mock
    WarehouseDAO warehouseDAO;

    @InjectMocks
    private GetWarehouseUseCaseImpl subject;

    @Test
    void getWarehouses_whenWarehousesExist_returnsWarehouses() {

        // Given
        final List<Warehouse> warehouses = List.of(sWarehouse.get(), sWarehouse.get());

        doReturn(warehouses).when(warehouseDAO).findAll();

        // When
        subject.getWarehouses();

        // Then
        verify(warehouseDAO).findAll();
    }

    @Test
    void getBySid_whenWarehouseExists_returnWarehouse() {

        // Given
        final Warehouse warehouse = faker.domain.warehouse().build();
        doReturn(Optional.of(warehouse)).when(warehouseDAO).findBySid(eq(warehouse.getSid()));

        // When
        final Warehouse result = subject.getBySid(warehouse.getSid());

        //Then
        assertThat(result).isEqualTo(warehouse);
    }

    @Test
    void getBySid_whenWarehouseDoesNotExist_throwWarehouseNotFoundException() {

        // Given
        final UUID randomUUID = UUID.randomUUID();

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> subject.getBySid(randomUUID);

        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(WarehouseNotFoundException.class);
    }
}
