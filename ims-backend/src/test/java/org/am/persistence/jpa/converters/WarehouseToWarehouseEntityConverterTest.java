package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Warehouse;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.AddressToAddressEntityConverter;
import org.am.infrastructure.persistence.converters.WarehouseToWarehouseEntityConverter;
import org.am.infrastructure.persistence.converters.WarehouseToWarehouseEntityConverterImpl;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.WarehouseEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class WarehouseToWarehouseEntityConverterTest {

    private final Faker faker = new Faker();

    private final WarehouseToWarehouseEntityConverter subject = new WarehouseToWarehouseEntityConverterImpl();

    @Mock
    private AddressToAddressEntityConverter addressToAddressEntityConverter;

    private final Warehouse warehouse = faker.domain.warehouse().build();

    private final AddressEntity addressEntity = faker.entity.address().build();

    WarehouseEntity warehouseEntity;

    @BeforeEach
    public void setUp() {

        warehouseEntity = buildWarehouseEntity(warehouse, addressEntity);
    }

    @Test
    public void convert_returnsConvertedWarehouseEntity() {

        // When
        final WarehouseEntity convertedWarehouseEntity = subject.convert(warehouse, addressEntity);

        // Then
        Assertions.assertThat(convertedWarehouseEntity.getSid()).isInstanceOf(UUID.class);
        assertThat(convertedWarehouseEntity)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(warehouseEntity);
    }

    private WarehouseEntity buildWarehouseEntity(Warehouse warehouse, AddressEntity address) {

        return WarehouseEntity.builder()
                .sid(warehouse.getSid())
                .name(warehouse.getName())
                .contactName(warehouse.getContactName())
                .phoneNumber(warehouse.getPhoneNumber())
                .createdAt(Instant.now())
                .address(address)
                .build();
    }
}
