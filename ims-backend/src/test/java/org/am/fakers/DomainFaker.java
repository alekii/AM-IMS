package org.am.fakers;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.util.TEST_CONSTANTS;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DomainFaker {

    private final Faker faker = new Faker();

    public Warehouse.Builder warehouse() {

        return Warehouse.builder()
                .sid(UUID.randomUUID())
                .name(faker.company().name())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .contactName(faker.name().fullName());
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
}
