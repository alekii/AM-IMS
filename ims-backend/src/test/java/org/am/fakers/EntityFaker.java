package org.am.fakers;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.am.fakers.util.TEST_CONSTANTS;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.BrandEntity;
import org.am.library.entities.CategoryEntity;
import org.am.library.entities.CountyEntity;
import org.am.library.entities.ProductEntity;
import org.am.library.entities.SupplierEntity;
import org.am.library.entities.TownEntity;
import org.am.library.entities.WarehouseEntity;
import org.am.library.entities.WarehouseTownCoverageEntity;

import java.time.Instant;
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

    public TownEntity.Builder town() {

        return TownEntity.builder()
                .sid(uuid())
                .name(faker.address().cityName())
                .county(this.county().build());
    }

    public CountyEntity.Builder county() {

        return CountyEntity.builder()
                .sid(uuid())
                .name(TEST_CONSTANTS.COUNTY);
    }

    public UUID uuid() {

        return UUID.randomUUID();
    }

    public WarehouseEntity.Builder warehouse() {

        return WarehouseEntity.builder()
                .sid(uuid())
                .name(faker.company().name())
                .phoneNumber(faker.phoneNumber().cellPhone())
                .contactName(faker.name().fullName())
                .address(this.address().build())
                .trackingNumbersCount(faker.number().randomDigit())
                .createdAt(Instant.now());
    }

    public WarehouseTownCoverageEntity.Builder warehouseTownCoverage() {

        return WarehouseTownCoverageEntity.builder()
                .warehouse(this.warehouse().build())
                .town(this.town().build());
    }

    private List<WarehouseTownCoverageEntity> buildWarehouseTownCoverages() {

        return Collections.singletonList(this.warehouseTownCoverage().build());
    }

    public SupplierEntity.Builder supplier() {

        return SupplierEntity.builder()
                .sid(uuid())
                .name(faker.company().name())
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .leadTime(faker.number().numberBetween(0, 30));
    }

    public ProductEntity.Builder product() {

        return ProductEntity.builder()
                .sid(uuid())
                .name(faker.beer().name())
                .category(this.category().build())
                .brand(this.brand().build())
                .supplied_by(this.supplier().build())
                .price(faker.number().randomDouble(2, 10, 100000))
                .quantity(faker.number().numberBetween(0, 1000))
                .description(faker.lorem().characters(200, 400, true))
                .discount(0.15)
                .warehouseSid(uuid());
    }

    public CategoryEntity.Builder category() {

        return CategoryEntity.builder()
                .name(faker.book().genre().toString())
                .sid(uuid());
    }

    public BrandEntity.Builder brand() {

        return BrandEntity.builder()
                .name(faker.book().genre().toString())
                .sid(uuid());
    }
}
