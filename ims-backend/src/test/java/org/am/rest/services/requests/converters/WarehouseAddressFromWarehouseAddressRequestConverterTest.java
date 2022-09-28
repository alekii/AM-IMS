package org.am.rest.services.requests.converters;

import com.github.javafaker.Faker;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseAddressFromWarehouseAddressRequestConverterTest {

    private final org.am.fakers.Faker domainFaker = new org.am.fakers.Faker();

    private final Faker faker = new Faker();

    private final WarehouseAddressFromWarehouseAddressRequestConverter subject = new WarehouseAddressFromWarehouseAddressRequestConverter();

    @Test
    void convert_returnsWarehouse_FromWarehouseAddressCreationRequest() {

        // Given
        final WarehouseAddressCreationRequest warehouseAddressCreationRequest =
                domainFaker.domain.warehouseAddressCreationrequest().build();
        final Address address = buildWarehouseAddress(warehouseAddressCreationRequest);

        // When
        final Address createdAddress
                = subject.convert(warehouseAddressCreationRequest);

        // Then
        assertThat(createdAddress).usingRecursiveComparison().isEqualTo(address);
    }

    private Address buildWarehouseAddress(WarehouseAddressCreationRequest warehouseAddressCreationRequest) {

        return Address.builder()
                .street(warehouseAddressCreationRequest.getStreet())
                .mapUrl(warehouseAddressCreationRequest.getMapUrl())
                .latitude(warehouseAddressCreationRequest.getLatitude())
                .longitude(warehouseAddressCreationRequest.getLongitude())
                .town(Town.builder()
                              .sid(warehouseAddressCreationRequest.getTown().getTownSid())
                              .build())
                .county(County.builder()
                                .sid(warehouseAddressCreationRequest.getCounty().getCountySid())
                                .build())
                .build();
    }
}
