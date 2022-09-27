package org.am.rest.services.requests.converters;

import com.github.javafaker.Faker;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.rest.services.requests.WarehouseUpdateRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseFromWarehouseUpdateRequestConverterTest {

    private static final UUID SID = UUID.randomUUID();

    private final Faker faker = new Faker();

    private final org.am.fakers.Faker domainFaker = new org.am.fakers.Faker();

    private final WarehouseFromWarehouseUpdateRequestConverter subject = new WarehouseFromWarehouseUpdateRequestConverter();

    @Test
    void convert_returnsWarehouse_FromWarehouseUpdateRequest() {

        // Given
        final WarehouseUpdateRequest warehouseUpdateRequest = domainFaker.domain.warehouseUpdateRequest().build();
        final Warehouse warehouse = buildWarehouse(warehouseUpdateRequest);

        // When
        final Warehouse updatedWarehouse
                = subject.convert(warehouseUpdateRequest, SID);

        // Then
        assertThat(updatedWarehouse).usingRecursiveComparison().isEqualTo(warehouse);
    }

    private Warehouse buildWarehouse(WarehouseUpdateRequest warehouseUpdateRequest) {

        return Warehouse.builder()
                .sid(SID)
                .name(warehouseUpdateRequest.getWarehouseName())
                .contactName(warehouseUpdateRequest.getContactName())
                .phoneNumber(warehouseUpdateRequest.getPhoneNumber())
                .address(Address.builder()
                                 .street(warehouseUpdateRequest.getAddress().getStreet())
                                 .mapUrl(warehouseUpdateRequest.getAddress().getMapUrl())
                                 .latitude(warehouseUpdateRequest.getAddress().getLatitude())
                                 .longitude(warehouseUpdateRequest.getAddress().getLongitude())
                                 .town(Town.builder()
                                               .sid(warehouseUpdateRequest.getAddress().getTown().getSid())
                                               .build())
                                 .build())
                .build();
    }
}
