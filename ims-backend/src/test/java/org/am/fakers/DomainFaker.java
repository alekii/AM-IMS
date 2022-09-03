package org.am.fakers;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.util.TEST_CONSTANTS;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.am.rest.services.responses.WarehouseMinimumResponse;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DomainFaker {

    private static final String PHONE_NUMBER = "+254701234567";

    private final Faker faker = new Faker();

    public Warehouse.Builder warehouse() {

        return Warehouse.builder()
                .sid(UUID.randomUUID())
                .name(faker.company().name())
                .phoneNumber(PHONE_NUMBER)
                .contactName(faker.name().fullName())
                .createdAt(Instant.now())
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
                .phoneNumber(PHONE_NUMBER)
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

    public WarehouseCreateRequest.Builder warehouseCreateRequest() {

        return WarehouseCreateRequest.builder()
                .warehouseName(faker.company().name())
                .contactName(faker.name().fullName())
                .phoneNumber(PHONE_NUMBER)
                .address(this.warehouseAddressCreationrequest().build());
    }

    public WarehouseAddressCreationRequest.Builder warehouseAddressCreationrequest() {

        return WarehouseAddressCreationRequest.builder()
                .latitude(Double.valueOf(faker.address().latitude()))
                .longitude(Double.valueOf(faker.address().longitude()))
                .street(faker.address().streetName())
                .mapUrl(faker.internet().url())
                .town(this.townRequest().build());
    }

    public WarehouseAddressCreationRequest.TownRequest.Builder townRequest() {

        return WarehouseAddressCreationRequest.TownRequest.builder()
                .sid(UUID.randomUUID())
                .county(this.countyRequest().build());
    }

    public WarehouseAddressCreationRequest.TownRequest.CountyRequest.Builder countyRequest() {

        return WarehouseAddressCreationRequest.TownRequest.CountyRequest.builder()
                .sid(UUID.randomUUID());
    }
}
