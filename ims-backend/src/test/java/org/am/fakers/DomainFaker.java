package org.am.fakers;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.Brand;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.County;
import org.am.domain.catalog.LineItem;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.util.TEST_CONSTANTS;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.entities.util.PurchaseStatus;
import org.am.rest.services.requests.SupplierCreateRequest;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.am.rest.services.requests.WarehouseUpdateRequest;
import org.am.rest.services.responses.SupplierResponse;
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
                .sid(uuid())
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
                .town(this.town().build())
                .county(this.county().build());
    }

    public County.Builder county() {

        return County.builder()
                .sid(uuid())
                .name(TEST_CONSTANTS.COUNTY);
    }

    public Town.Builder town() {

        return Town.builder()
                .sid(uuid())
                .name(faker.address().cityName());
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
                                               .build())
                                 .county(WarehouseFullResponse.AddressResponse.CountyResponse.builder()
                                                 .sid(UUID.randomUUID())
                                                 .name(faker.address().state())
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

    public WarehouseUpdateRequest.Builder warehouseUpdateRequest() {

        return WarehouseUpdateRequest.builder()
                .warehouseName(faker.company().name())
                .contactName(faker.name().fullName())
                .phoneNumber("+254701096760")
                .address(this.warehouseAddressUpdateRequest().build());
    }

    public WarehouseUpdateRequest.WarehouseAddressUpdateRequest.Builder warehouseAddressUpdateRequest() {

        return WarehouseUpdateRequest.WarehouseAddressUpdateRequest.builder()
                .street(faker.address().streetName())
                .mapUrl(faker.internet().url())
                .latitude(Double.valueOf(faker.address().latitude()))
                .longitude(Double.valueOf(faker.address().longitude()))
                .town(WarehouseUpdateRequest.WarehouseAddressUpdateRequest.TownRequest.builder()
                              .townSid(UUID.randomUUID()).build());
    }

    public WarehouseAddressCreationRequest.Builder warehouseAddressCreationrequest() {

        return WarehouseAddressCreationRequest.builder()
                .latitude(Double.valueOf(faker.address().latitude()))
                .longitude(Double.valueOf(faker.address().longitude()))
                .street(faker.address().streetName())
                .mapUrl(faker.internet().url())
                .town(this.townRequest().build())
                .county(this.countyRequest().build());
    }

    public WarehouseAddressCreationRequest.TownRequest.Builder townRequest() {

        return WarehouseAddressCreationRequest.TownRequest.builder()
                .townSid(UUID.randomUUID());
    }

    public WarehouseAddressCreationRequest.CountyRequest.Builder countyRequest() {

        return WarehouseAddressCreationRequest.CountyRequest.builder()
                .countySid(UUID.randomUUID());
    }

    public WarehouseProjection.Builder warehouseProjection() {

        return WarehouseProjection.builder()
                .sid(uuid())
                .name(faker.company().name())
                .contactName(faker.name().fullName())
                .phoneNumber(PHONE_NUMBER)
                .trackingNumberCount(faker.random().nextInt(353466634, 646456436))
                .addressMapUrl(faker.internet().url())
                .addressLatitude(Double.valueOf(faker.address().latitude()))
                .addressLongitude(Double.valueOf(faker.address().longitude()))
                .addressStreet(faker.address().streetName())
                .townSid(UUID.randomUUID())
                .townName(faker.address().cityName())
                .countySid(UUID.randomUUID())
                .countyName(faker.address().state());
    }

    public Supplier.Builder supplier() {

        return Supplier.builder()
                .sid(uuid())
                .name(faker.company().name())
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .leadTime(faker.number().numberBetween(0, 30));
    }

    public SupplierCreateRequest.Builder supplierCreateRequest() {

        return SupplierCreateRequest.builder()
                .email(faker.internet().emailAddress())
                .leadTime(faker.number().numberBetween(0, 30))
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .name(faker.company().name());
    }

    public SupplierUpdateRequest.Builder supplierUpdateRequest() {

        return SupplierUpdateRequest.builder()
                .email(faker.internet().emailAddress())
                .leadTime(faker.number().numberBetween(0, 30))
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .name(faker.company().name());
    }

    public SupplierResponse.SupplierResponseBuilder supplierResponse() {

        return SupplierResponse.builder()
                .sid(uuid())
                .name(faker.company().name())
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .leadTime(faker.number().numberBetween(0, 30));
    }

    public Product.Builder product() {

        return Product.builder()
                .sid(uuid())
                .name(faker.beer().name())
                .category(this.category().build())
                .brand(this.brand().build())
                .supplier(this.supplier().build())
                .price(faker.number().randomDouble(2, 10, 100000))
                .quantity(faker.number().numberBetween(0, 1000))
                .description(faker.lorem().characters(200, 400, true))
                .discount(0.15)
                .warehouseSid(uuid());
    }

    public Category.Builder category() {

        return Category.builder()
                .name(faker.book().genre().toString())
                .sid(uuid());
    }

    public Brand.Builder brand() {

        return Brand.builder()
                .name(faker.book().genre().toString())
                .sid(uuid());
    }

    public Purchase.Builder purchase() {

        return Purchase.builder()
                .sid(uuid())
                .invoice(faker.number().numberBetween(0, 999999999))
                .status(PurchaseStatus.PENDING_APPROVAL)
                .dateReceived(Instant.now())
                .warehouse(this.warehouse().build())
                .supplier(this.supplier().build())
                .totalAmount(faker.number().randomDouble(2, 10, 9999999));
    }

    public LineItem.Builder lineItem() {

        return LineItem.builder()
                .sid(uuid())
                .purchase(this.purchase().build())
                .product((this.product().build()))
                .quantity(faker.number().numberBetween(1, 10000))
                .price(faker.number().randomDouble(2, 10, 9999999))
                .sid(UUID.randomUUID());
    }
}
