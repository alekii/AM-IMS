package org.am.fakers;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.am.fakers.util.TEST_CONSTANTS;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.CountyEntity;
import org.am.library.entities.TownEntity;
import org.am.library.entities.WarehouseEntity;
import org.am.library.entities.WarehouseTownCoverageEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityFaker {

    private final Faker faker = new Faker();

    public AddressEntity.Builder address() {

        return AddressEntity.builder()
                .street(faker.address().streetName())
                .mapUrl(faker.internet().url())
                .latitude(Double.valueOf(faker.address().latitude()))
                .longitude(Double.valueOf(faker.address().longitude()))
                .town(this.town().build());
    }

    public WarehouseEntity.Builder buildWarehouse() {

        return warehouse(new ArrayList<>());
    }

    public WarehouseEntity.Builder buildWarehousewithCoverages() {

        return warehouse(buildWarehouseTownCoverages());
    }

    public CountyEntity.Builder county() {

        return CountyEntity.builder()
                .sid(uuid())
                .name(TEST_CONSTANTS.COUNTY);
    }

    public TownEntity.Builder town() {

        return TownEntity.builder()
                .sid(uuid())
                .name(faker.address().cityName())
                .county(this.county().build());
    }

    public UUID uuid() {

        return UUID.randomUUID();
    }

    public WarehouseEntity.Builder warehouse(List<WarehouseTownCoverageEntity> warehouseTownCoverages) {

        return WarehouseEntity.builder()
                .sid(uuid())
                .name(faker.company().name())
                .phoneNumber(faker.phoneNumber().cellPhone())
                .contactName(faker.name().fullName())
                .warehouseAddress(this.address().build())
                .trackingNumbersCount(faker.number().randomDigit());
    }

    public WarehouseEntity.Builder warehouseBuilder() {

        return warehouse(buildWarehouseTownCoverages());
    }

    public WarehouseTownCoverageEntity.Builder warehouseTownCoverage() {

        return WarehouseTownCoverageEntity.builder()
                .warehouse(this.buildWarehouse().build())
                .town(this.town().build());
    }

    private List<WarehouseTownCoverageEntity> buildWarehouseTownCoverages() {

        return Collections.singletonList(this.warehouseTownCoverage().build());
    }
}
