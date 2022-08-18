package org.am.fakers;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.am.library.entities.Address;
import org.am.library.entities.Warehouse;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityFaker {

    private final Faker faker = new Faker();

    public UUID uuid() {

        return UUID.randomUUID();
    }

    public Warehouse.Builder warehouse(Address address) {

        return Warehouse.builder()
                .sid(uuid())
                .name(faker.company().name())
                .phoneNumber(String.valueOf(faker.phoneNumber()))
                .contactName(faker.name().fullName())
                .warehouseAddress(address)
                .trackingNumbersCount(faker.number().randomDigit());
    }

    public Warehouse.Builder warehouse() {

        return warehouse();
    }
}
