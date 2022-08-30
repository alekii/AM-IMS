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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class WarehouseConverterTest {

    private final Faker faker = new Faker();

    private final WarehouseConverter subject = new WarehouseConverterImpl();

    @Test
    void convert_returnsAWarehouse() {

        // Given
        final WarehouseEntity entity = faker.entity.warehouse().build();

        final County county = County.builder()
                .sid(entity.getAddress().getTown().getCounty().getSid())
                .name(entity.getAddress().getTown().getCounty().getName())
                .build();

        final Town town = Town.builder()
                .sid(entity.getAddress().getTown().getSid())
                .name(entity.getAddress().getTown().getName())
                .county(county)
                .build();

        final Address address = Address.builder()
                .street(entity.getAddress().getStreet())
                .mapUrl(entity.getAddress().getMapUrl())
                .longitude(entity.getAddress().getLongitude())
                .latitude(entity.getAddress().getLatitude())
                .town(town)
                .build();

        final Warehouse expected = Warehouse.builder()
                .sid(entity.getSid())
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .contactName(entity.getContactName())
                .address(address)
                .build();

        // When
        final Warehouse warehouse = subject.convert(entity);

        // Then
        assertThat(warehouse).usingRecursiveComparison().isEqualTo(expected);
    }
}
