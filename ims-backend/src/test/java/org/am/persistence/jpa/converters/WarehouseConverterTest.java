package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.WarehouseConverter;
import org.am.infrastructure.persistence.converters.WarehouseConverterImpl;
import org.am.library.entities.WarehouseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class WarehouseConverterTest {

    private final Faker faker = new Faker();

    private Supplier<Warehouse> sWarehouse = () -> faker.domain.warehouse().build();

    private final WarehouseConverter subject = new WarehouseConverterImpl();

    @Test
    void convert_returnsAWarehouse() {

        // Given
        final WarehouseEntity entity = faker.entity.warehouse().build();

        final Warehouse expected = buildWarehouse(entity);

        // When
        final Warehouse warehouse = subject.convert(entity);

        // Then
        assertThat(warehouse).usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(expected);
    }

    private Warehouse buildWarehouse(WarehouseEntity entity) {

        return Warehouse.builder()
                .sid(entity.getSid())
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .contactName(entity.getContactName())
                .createdAt(Instant.now())
                .address(Address.builder()
                                 .street(entity.getAddress().getStreet())
                                 .mapUrl(entity.getAddress().getMapUrl())
                                 .longitude(entity.getAddress().getLongitude())
                                 .latitude(entity.getAddress().getLatitude())
                                 .town(Town.builder()
                                               .sid(entity.getAddress().getTown().getSid())
                                               .name(entity.getAddress().getTown().getName())
                                               .build())
                                 .county(County.builder()
                                                 .sid(entity.getAddress().getTown().getCounty().getSid())
                                                 .name(entity.getAddress().getTown().getCounty().getName())
                                                 .build())
                                 .build())
                .build();
    }
}
