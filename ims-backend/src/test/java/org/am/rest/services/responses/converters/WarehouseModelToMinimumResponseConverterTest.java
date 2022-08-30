package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.Faker;
import org.am.rest.services.responses.WarehouseMinimumResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WarehouseModelToMinimumResponseConverterTest {

    private final Faker faker = new Faker();

    private final WarehouseModelToMinimumResponseConverter warehouseModelToMinimumResponseConverter
            = new WarehouseModelToMinimumResponseConverter();

    @Test
    void convert_Warehouse_returnsWarehouseModelToWarehouseFullResponse() {

        // Given
        final Warehouse warehouse = faker.domain.warehouse().build();
        final WarehouseMinimumResponse warehouseMinimumResponse = buildWarehouseMinimumResponse(warehouse);

        // When
        final WarehouseMinimumResponse response = warehouseModelToMinimumResponseConverter.convert(warehouse);

        // Then
        assertThat(warehouseMinimumResponse).usingRecursiveComparison().isEqualTo(response);
    }

    @Test
    void convert_whenWarehouseWithTownNull_convertsWarehouseModelToWarehouseFullResponse() {

        // Given
        final Warehouse warehouse = buildWarehouseWithTownNull();

        // When
        final WarehouseMinimumResponse convertedWarehouseMinimumResponse =
                warehouseModelToMinimumResponseConverter.convert(warehouse);

        // Then
        Assert.assertNull(convertedWarehouseMinimumResponse.getAddress().getTown());
    }

    @Test
    void convert_whenWarehouseWithCountyNull_convertsWarehouseModelToWarehouseFullResponse() {

        // Given
        final Warehouse warehouse = buildWarehouseWithCountyNull();

        // When
        final WarehouseMinimumResponse convertedWarehouseMinimumResponse =
                warehouseModelToMinimumResponseConverter.convert(warehouse);

        // Then
        Assert.assertNull(convertedWarehouseMinimumResponse.getAddress().getCounty());
    }

    private Warehouse buildWarehouseWithCountyNull() {

        return Warehouse.builder()
                .address(Address.builder()
                                 .town(Town.builder()
                                               .name("Nairobi")
                                               .county(null)
                                               .build())
                                 .build())
                .build();
    }

    private Warehouse buildWarehouseWithTownNull() {

        return Warehouse.builder()
                .address(Address.builder()
                                 .street("Fourth Street")
                                 .town(null)
                                 .build())
                .build();
    }

    private WarehouseMinimumResponse buildWarehouseMinimumResponse(Warehouse warehouse) {

        return WarehouseMinimumResponse.builder()
                .sid(warehouse.getSid())
                .name(warehouse.getName())
                .address(WarehouseMinimumResponse.AddressResponse.builder()
                                 .street(warehouse.getAddress().getStreet())
                                 .town(warehouse.getAddress().getTown().getName())
                                 .county(warehouse.getAddress().getTown().getCounty().getName())
                                 .build())

                .build();
    }
}
