package org.am.rest.services;

import org.am.domain.api.GetWarehouseUseCase;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.Faker;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.am.rest.services.responses.WarehouseMinimumResponse;
import org.am.rest.services.responses.converters.WarehouseModelToFullResponseConverter;
import org.am.rest.services.responses.converters.WarehouseModelToMinimumResponseConverter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    private final Faker faker = new Faker();

    private final Supplier<Warehouse> sWarehouse = () -> faker.domain.warehouse().build();

    @Mock
    private GetWarehouseUseCase getWarehouseUseCase;

    @Mock
    private WarehouseModelToFullResponseConverter warehouseModelToFullResponseConverter;

    @Mock
    private WarehouseModelToMinimumResponseConverter warehouseModelToMinimumResponseConverter;

    private final Supplier<WarehouseMinimumResponse> sWarehouseMinimumResponse = () -> faker.domain.warehouseMinimumResponse().build();

    private final Supplier<WarehouseFullResponse> sWarehouseFullResponse = () -> faker.domain.warehouseFullResponse().build();

    @InjectMocks
    private WarehouseService subject;

    @Test
    void getWarehouses_whenWarehouseListIsEmpty_returnListSuccessfully() {

        // Given
        final List<Warehouse> warehouses = Collections.emptyList();

        when(getWarehouseUseCase.getWarehouses()).thenReturn(warehouses);

        // When
        final List<WarehouseMinimumResponse> result = subject.getWarehouses();

        // Then
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    void getWarehouses_whenWarehouseListNotEmpty_returnListSuccessfully() {

        // Given
        final List<Warehouse> warehouses = List.of(sWarehouse.get(), sWarehouse.get());

        final WarehouseMinimumResponse warehouseMinimumResponse1 = sWarehouseMinimumResponse.get();
        final WarehouseMinimumResponse warehouseMinimumResponse2 = sWarehouseMinimumResponse.get();

        when(getWarehouseUseCase.getWarehouses()).thenReturn(warehouses);

        doReturn(warehouseMinimumResponse1, warehouseMinimumResponse2)
                .when(warehouseModelToMinimumResponseConverter)
                .convert(any(Warehouse.class));

        // When
        final List<WarehouseMinimumResponse> result = subject.getWarehouses();

        // Then
        assertThat(result).containsExactlyInAnyOrder(warehouseMinimumResponse1, warehouseMinimumResponse2);
    }

    @Test
    void findBySid_whenWarehouseExists_returnsWarehouseSuccessfully() {

        // Given
        final Warehouse warehouse = sWarehouse.get();
        final WarehouseFullResponse warehouseFullResponse = sWarehouseFullResponse.get();

        doReturn(warehouse)
                .when(getWarehouseUseCase)
                .getBySid(any(UUID.class));

        doReturn(warehouseFullResponse)
                .when(warehouseModelToFullResponseConverter)
                .convert(any(Warehouse.class));

        // When
        final WarehouseFullResponse result = subject.findBySid(warehouse.getSid());

        // Then
        assertThat(result.getSid()).isEqualTo(warehouseFullResponse.getSid());
    }
}
