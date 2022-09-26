package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Address;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.AddressToAddressEntityConverter;
import org.am.infrastructure.persistence.converters.AddressToAddressEntityConverterImpl;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.TownEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AddressToAddressEntityConverterTest {

    private final Faker faker = new Faker();

    private final AddressToAddressEntityConverter subject = new AddressToAddressEntityConverterImpl();

    @Test
    void convert_returnsConvertedAddressEntity() {

        // Given
        final Address address = faker.domain.address().build();
        final TownEntity townEntity = faker.entity.town().build();
        final AddressEntity addressEntity = faker.entity.address()
                .mapUrl(address.getMapUrl())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .street(address.getStreet())
                .town(townEntity)
                .build();

        // When
        final AddressEntity convertedAddressEntity = subject.convert(address, townEntity);

        // Then
        assertThat(convertedAddressEntity)
                .usingRecursiveComparison()
                .isEqualTo(addressEntity);
    }
}
