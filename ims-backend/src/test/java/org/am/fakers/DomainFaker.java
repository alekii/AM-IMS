package org.am.fakers;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.util.TEST_CONSTANTS;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.am.rest.services.responses.WarehouseMinimumResponse;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DomainFaker {

    private final Faker faker = new Faker();

    public Warehouse.Builder warehouse() {

        return Warehouse.builder()
                .sid(UUID.randomUUID())
                .name(faker.company().name())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .contactName(faker.name().fullName())
                .address(this.address().build());
    }

    public Address.Builder address() {

        return Address.builder()
                .street(faker.address().streetName())
                .mapUrl(faker.internet().url())
                .latitude(Double.valueOf(faker.address().latitude()))
                .longitude(Double.valueOf(faker.address().longitude()))
                .town(this.town().build());
    }

    public County.Builder county() {

        return County.builder()
                .sid(uuid())
                .name(TEST_CONSTANTS.COUNTY);
    }

    public Town.Builder town() {

        return Town.builder()
                .sid(uuid())
                .name(faker.address().cityName())
                .county(this.county().build());
    }

    private UUID uuid() {

        return UUID.randomUUID();
    }

    public WarehouseFullResponse.WarehouseFullResponseBuilder warehouseFullResponse() {

        return WarehouseFullResponse.builder()
                .name(faker.company().name())
                .sid(UUID.randomUUID())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .contactName(faker.name().fullName())
                .address(WarehouseFullResponse.AddressResponse.builder()
                                 .street(faker.address().streetName())
                                 .mapUrl(faker.internet().url())
                                 .longitude(Double.valueOf(faker.address().longitude()))
                                 .latitude(Double.valueOf(faker.address().latitude()))
                                 .town(WarehouseFullResponse.AddressResponse.TownResponse.builder()
                                               .name(faker.address().cityName())
                                               .sid(UUID.randomUUID())
                                               .county(WarehouseFullResponse.AddressResponse.TownResponse.CountyResponse.builder()
                                                               .sid(UUID.randomUUID())
                                                               .name(faker.address().state())
                                                               .build())
                                               .build())
                                 .build());
    }

    public WarehouseMinimumResponse.WarehouseMinimumResponseBuilder warehouseMinimumResponse() {

        return WarehouseMinimumResponse.builder()
                .name(faker.company().name())
                .sid(UUID.randomUUID())
                .address(WarehouseMinimumResponse.AddressResponse.builder()
                                 .street(faker.address().streetName())
                                 .town(faker.address().cityName())
                                 .county(faker.address().state())
                                 .build());
    }
}
