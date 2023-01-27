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
import org.am.domain.catalog.ProductImage;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.fakers.util.TEST_CONSTANTS;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.entities.util.PurchaseStatus;
import org.am.rest.services.requests.BrandCreationRequest;
import org.am.rest.services.requests.CategoryCreationRequest;
import org.am.rest.services.requests.ProductCreateRequest;
import org.am.rest.services.requests.ProductImageCreateRequest;
import org.am.rest.services.requests.ProductUpdateRequest;
import org.am.rest.services.requests.SupplierCreateRequest;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.am.rest.services.requests.WarehouseUpdateRequest;
import org.am.rest.services.responses.BrandResponse;
import org.am.rest.services.responses.CategoryResponse;
import org.am.rest.services.responses.ProductFullResponse;
import org.am.rest.services.responses.ProductImageResponse;
import org.am.rest.services.responses.ProductMinimumResponse;
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
                .sid(uuid())
                .phoneNumber(PHONE_NUMBER)
                .contactName(faker.name().fullName())
                .address(WarehouseFullResponse.AddressResponse.builder()
                                 .street(faker.address().streetName())
                                 .mapUrl(faker.internet().url())
                                 .longitude(Double.valueOf(faker.address().longitude()))
                                 .latitude(Double.valueOf(faker.address().latitude()))
                                 .town(WarehouseFullResponse.AddressResponse.TownResponse.builder()
                                               .name(faker.address().cityName())
                                               .sid(uuid())
                                               .build())
                                 .county(WarehouseFullResponse.AddressResponse.CountyResponse.builder()
                                                 .sid(uuid())
                                                 .name(faker.address().state())
                                                 .build())
                                 .build());
    }

    public WarehouseMinimumResponse.WarehouseMinimumResponseBuilder warehouseMinimumResponse() {

        return WarehouseMinimumResponse.builder()
                .name(faker.company().name())
                .sid(uuid())
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
                              .townSid(uuid()).build());
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
                .townSid(uuid());
    }

    public WarehouseAddressCreationRequest.CountyRequest.Builder countyRequest() {

        return WarehouseAddressCreationRequest.CountyRequest.builder()
                .countySid(uuid());
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
                .townSid(uuid())
                .townName(faker.address().cityName())
                .countySid(uuid())
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
                .name(faker.name().fullName())
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
                .name(faker.book().genre())
                .sid(uuid());
    }

    public Purchase.Builder purchase() {

        return Purchase.builder()
                .sid(uuid())
                .invoice(faker.number().numberBetween(0, 999999999))
                .status(PurchaseStatus.PENDING_APPROVAL)
                .dateReceived(Instant.now())
                .warehouseSid(uuid())
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
                .sid(uuid());
    }

    public ProductImage.Builder productImage() {

        return ProductImage.builder()
                .sid(uuid())
                .productSid(uuid())
                .imagePath(faker.internet().url());
    }

    public ProductCreateRequest.Builder productCreateRequest() {

        return ProductCreateRequest.builder()
                .name(faker.funnyName().name())
                .price(faker.number().randomDouble(2, 1, 99999))
                .sku(faker.funnyName().name())
                .category(ProductCreateRequest.CategoryRequest.builder()
                                  .sid(uuid())
                                  .name(faker.name().name())
                                  .build())
                .brand(ProductCreateRequest.BrandRequest.builder()
                               .sid(uuid())
                               .name(faker.book().author())
                               .build())
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .sid(uuid())
                                  .email(faker.internet().emailAddress())
                                  .name(faker.company().name())
                                  .leadTime(faker.number().numberBetween(0, 30))
                                  .phoneNumber(faker.phoneNumber().phoneNumber())
                                  .build())
                .dateReceived(Instant.now())
                .receivedBy(faker.name().fullName())
                .quantity(faker.number().numberBetween(1, 99999))
                .warehouseSid(uuid())
                .discount(faker.number().randomDouble(2, 10, 60));
    }

    public ProductUpdateRequest.Builder productUpdateRequest() {

        return ProductUpdateRequest.builder()
                .sid(uuid())
                .name(faker.funnyName().name())
                .price(faker.number().randomDouble(2, 1, 99999))
                .sku(faker.funnyName().name())
                .category(ProductUpdateRequest.CategoryRequest.builder()
                                  .sid(uuid())
                                  .name(faker.name().name())
                                  .build())
                .brand(ProductUpdateRequest.BrandRequest.builder()
                               .sid(uuid())
                               .name(faker.book().author())
                               .build())
                .quantity(faker.number().numberBetween(1, 99999))
                .discount(faker.number().randomDouble(2, 10, 60));
    }

    public ProductImageCreateRequest.Builder productImageCreateRequest() {

        return ProductImageCreateRequest.builder()
                .productSid(uuid())
                .imagePath(faker.internet().url());
    }

    public BrandCreationRequest.Builder brandCreationRequest() {

        return BrandCreationRequest.builder()
                .name(faker.name().fullName());
    }

    public CategoryCreationRequest.Builder categoryCreationRequest() {

        return CategoryCreationRequest.builder()
                .name(faker.name().name());
    }

    public ProductFullResponse.ProductFullResponseBuilder productFullResponse() {

        return ProductFullResponse.builder()
                .sid(uuid())
                .brand(ProductFullResponse.BrandResponse.builder()
                               .sid(uuid())
                               .name(faker.name().name())
                               .build())
                .category(ProductFullResponse.CategoryResponse.builder()
                                  .sid(uuid())
                                  .name(faker.name().name())
                                  .build())
                .supplier(ProductFullResponse.SupplierResponse.builder()
                                  .sid(uuid())
                                  .name(faker.company().name())
                                  .build())
                .price(faker.number().randomDouble(2, 1, 99999))
                .sku(faker.funnyName().name())
                .description(faker.lorem().paragraphs(1).toString())
                .name(faker.funnyName().name())
                .dateReceived(Instant.now())
                .receivedBy(faker.name().fullName())
                .quantity(faker.number().numberBetween(1, 99999))
                .warehouseSid(uuid())
                .discount(faker.number().randomDouble(2, 10, 60));
    }

    public ProductMinimumResponse.ProductMinimumResponseBuilder productMinimumResponse() {

        return ProductMinimumResponse.builder()
                .name(faker.name().name())
                .sid(uuid())
                .discount(faker.number().randomDouble(2, 10, 60))
                .price(faker.number().randomDouble(2, 1, 99999));
    }

    public ProductImageResponse.ProductImageResponseBuilder productImageResponse() {

        return ProductImageResponse.builder()
                .sid(uuid())
                .id(faker.number().numberBetween(0, 1024))
                .imagePath(faker.internet().url());
    }

    public BrandResponse.Builder brandResponse() {

        return BrandResponse.builder()
                .sid(uuid())
                .name(faker.company().name());
    }

    public CategoryResponse.Builder categoryResponse() {

        return CategoryResponse.builder()
                .sid(uuid())
                .name(faker.company().name());
    }
}
