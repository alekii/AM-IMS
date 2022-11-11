package org.am.persistence.jpa.converters;

import org.am.fakers.Faker;
import org.am.infrastructure.warehouses.converters.WarehouseToKafkaPayloadConverter;
import org.am.infrastructure.warehouses.converters.WarehouseToKafkaPayloadConverterImpl;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.events.resources.WarehouseKafkaPayload;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseToKafkaPayloadConverterTest {

    private final Faker faker = new Faker();

    private final WarehouseToKafkaPayloadConverter subject = new WarehouseToKafkaPayloadConverterImpl();

    @Test
    public void convert_returnsConvertedWarehouseKafkaPayload() {

        // Given
        final WarehouseProjection warehouseProjection = faker.domain.warehouseProjection().build();
        final WarehouseKafkaPayload warehouseKafkaPayload = buildWarehouseKafkaPayload(warehouseProjection);

        // When
        final WarehouseKafkaPayload expectedWarehouseKafkaPayload = subject.convert(warehouseProjection);

        // Then
        assertThat(expectedWarehouseKafkaPayload).usingRecursiveComparison().isEqualTo(warehouseKafkaPayload).ignoringFields("createdAt");
    }

    private WarehouseKafkaPayload buildWarehouseKafkaPayload(WarehouseProjection warehouseProjection) {

        return WarehouseKafkaPayload.builder()
                .warehouseSid(warehouseProjection.getSid())
                .warehouseName(warehouseProjection.getName())
                .phoneNumber(warehouseProjection.getPhoneNumber())
                .contactName(warehouseProjection.getContactName())
                .trackingNumberCount(warehouseProjection.getTrackingNumberCount())
                .addressPayload(buildAddressPayload(warehouseProjection))
                .build();
    }

    private WarehouseKafkaPayload.AddressPayload buildAddressPayload(WarehouseProjection warehouseProjection) {

        return WarehouseKafkaPayload.AddressPayload.builder()
                .streetName(warehouseProjection.getAddressStreet())
                .mapUrl(warehouseProjection.getAddressMapUrl())
                .latitude(warehouseProjection.getAddressLatitude())
                .longitude(warehouseProjection.getAddressLongitude())
                .townPayload(WarehouseKafkaPayload.AddressPayload.TownPayload.builder()
                                     .townSid(warehouseProjection.getTownSid())
                                     .name(warehouseProjection.getTownName())
                                     .build())
                .countyPayload(WarehouseKafkaPayload.AddressPayload.CountyPayload.builder()
                                       .countySid(warehouseProjection.getCountySid())
                                       .name(warehouseProjection.getCountyName())
                                       .build())
                .build();
    }
}
