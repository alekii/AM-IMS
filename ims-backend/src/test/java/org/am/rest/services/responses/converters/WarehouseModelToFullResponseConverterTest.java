package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.Faker;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WarehouseModelToFullResponseConverterTest {

    private final Faker faker = new Faker();

    private final WarehouseModelToFullResponseConverter warehouseModelToFullResponseConverter
            = new WarehouseModelToFullResponseConverter();

    @Test
    void convert_Warehouse_returnsWarehouseModelToWarehouseFullResponse() {

        // Given
        final Warehouse warehouse = faker.domain.warehouse().build();
        final WarehouseFullResponse warehouseFullResponse = buildWarehouseFullResponse(warehouse);

        // When
        final WarehouseFullResponse response = warehouseModelToFullResponseConverter.convert(warehouse);

        // Then
        assertThat(warehouseFullResponse).usingRecursiveComparison().isEqualTo(response);
    }

    @Test
    void convert_whenWarehouseFieldsContainsNullValues_convertsWarehouseModelToWarehouseFullResponse() {

        // Given
        final Warehouse warehouse = Warehouse.builder().build();

        // When
        final WarehouseFullResponse convertedWarehouseFullResponse =
                warehouseModelToFullResponseConverter.convert(warehouse);

        // Then
        assertThat(convertedWarehouseFullResponse).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(warehouse);
    }

    @Test
    void convert_whenWarehouseWithTownNull_convertsWarehouseModelToWarehouseFullResponse() {

        // Given
        final Warehouse warehouse = buildWarehouseWithTownNull();

        // When
        final WarehouseFullResponse convertedWarehouseFullResponse =
                warehouseModelToFullResponseConverter.convert(warehouse);

        // Then
        Assert.assertNull(convertedWarehouseFullResponse.getAddress().getTown().getSid());
    }

    @Test
    void convert_whenWarehouseWithCountyNull_convertsWarehouseModelToWarehouseFullResponse() {

        // Given
        final Warehouse warehouse = buildWarehouseWithCountyNull();

        // When
        final WarehouseFullResponse convertedWarehouseFullResponse =
                warehouseModelToFullResponseConverter.convert(warehouse);

        // Then
        Assert.assertNull(convertedWarehouseFullResponse.getAddress().getCounty().getSid());
    }

    private Warehouse buildWarehouseWithCountyNull() {

        return Warehouse.builder()
                .address(Address.builder()
                                 .town(faker.domain.town().build())
                                 .build())
                .build();
    }

    private Warehouse buildWarehouseWithTownNull() {

        return Warehouse.builder()
                .address(Address.builder()
                                 .street("Fourth Street")
                                 .county(faker.domain.county().build())
                                 .build())
                .build();
    }

    private WarehouseFullResponse buildWarehouseFullResponse(Warehouse warehouse) {

        return WarehouseFullResponse.builder()
                .sid(warehouse.getSid())
                .phoneNumber(warehouse.getPhoneNumber())
                .contactName(warehouse.getContactName())
                .name(warehouse.getName())
                .address(buildAddressResponse(warehouse.getAddress()))
                .build();
    }

    private WarehouseFullResponse.AddressResponse buildAddressResponse(Address address) {

        return WarehouseFullResponse.AddressResponse.builder()
                .street(address.getStreet())
                .mapUrl(address.getMapUrl())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .town(WarehouseFullResponse.AddressResponse.TownResponse.builder()
                              .sid(address.getTown().getSid())
                              .name(address.getTown().getName())
                              .build())
                .county(WarehouseFullResponse.AddressResponse.CountyResponse.builder()
                                .name(address.getCounty().getName())
                                .sid(address.getCounty().getSid())
                                .build())
                .build();
    }
}
