package org.am.rest.services.requests.converters;

import com.github.javafaker.Faker;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseFromWarehouseCreateRequestConverterTest {

    private final Faker faker = new Faker();

    private final WarehouseAddressFromWarehouseAddressRequestConverter addressRequestConverter = new WarehouseAddressFromWarehouseAddressRequestConverter();

    private final WarehouseFromWarehouseCreateRequestConverter subject =
            new WarehouseFromWarehouseCreateRequestConverter(addressRequestConverter);

    @Test
    void convert_returnsWarehouse_FromWarehouseCreateRequest() {

        // Given
        final WarehouseCreateRequest warehouseCreateRequest = buildWarehouseCreateRequest();
        final Warehouse warehouse = buildWarehouse(warehouseCreateRequest);

        // When
        final Warehouse createdWarehouse
                = subject.convert(warehouseCreateRequest);

        // Then
        assertThat(createdWarehouse).usingRecursiveComparison().ignoringFields("sid").isEqualTo(warehouse);
    }

    private Warehouse buildWarehouse(WarehouseCreateRequest warehouseCreateRequest) {

        return Warehouse.builder()
                .name(warehouseCreateRequest.getWarehouseName())
                .contactName(warehouseCreateRequest.getContactName())
                .phoneNumber(warehouseCreateRequest.getPhoneNumber())
                .address(Address.builder()
                                 .street(warehouseCreateRequest.getAddress().getStreet())
                                 .mapUrl(warehouseCreateRequest.getAddress().getMapUrl())
                                 .latitude(warehouseCreateRequest.getAddress().getLatitude())
                                 .longitude(warehouseCreateRequest.getAddress().getLongitude())
                                 .town(Town.builder()
                                               .sid(warehouseCreateRequest.getAddress().getTown().getTownSid())
                                               .build())
                                 .county(County.builder()
                                                 .sid(warehouseCreateRequest.getAddress().getCounty().getCountySid())
                                                 .build())
                                 .build())
                .build();
    }

    private WarehouseCreateRequest buildWarehouseCreateRequest() {

        return WarehouseCreateRequest.builder()
                .warehouseName(faker.company().name())
                .contactName(faker.name().fullName())
                .phoneNumber(faker.phoneNumber().cellPhone())
                .address(WarehouseAddressCreationRequest.builder()
                                 .street(faker.address().streetName())
                                 .mapUrl(faker.internet().url())
                                 .latitude(Double.valueOf(faker.address().latitude()))
                                 .longitude(Double.valueOf(faker.address().longitude()))
                                 .town(WarehouseAddressCreationRequest.TownRequest.builder()
                                               .townSid(UUID.randomUUID())
                                               .build())
                                 .county(WarehouseAddressCreationRequest.CountyRequest.builder()
                                                 .countySid(UUID.randomUUID())
                                                 .build())
                                 .build())
                .build();
    }
}
