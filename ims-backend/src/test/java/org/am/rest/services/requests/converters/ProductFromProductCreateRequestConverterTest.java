package org.am.rest.services.requests.converters;

import com.github.javafaker.Faker;
import org.am.domain.catalog.Brand;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.requests.ProductCreateRequest;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductFromProductCreateRequestConverterTest {

    private final Faker faker = new Faker();

    private final ProductFromProductCreateRequestConverter subject = new ProductFromProductCreateRequestConverter();

    @Test
    public void convert_returnsProduct_FromProductCreateRequest() {

        // Given
        final ProductCreateRequest productCreateRequest = buildProductCreateRequest();
        final Product product = buildProduct(productCreateRequest);

        // When
        final Product createdProduct = subject.convert(productCreateRequest);

        // Then
        assertThat(createdProduct)
                .usingRecursiveComparison()
                .ignoringFields("sid")
                .isEqualTo(product);
    }

    private Product buildProduct(ProductCreateRequest productCreateRequest) {

        return Product.builder()
                .name(productCreateRequest.getName())
                .price(productCreateRequest.getPrice())
                .discount(productCreateRequest.getDiscount())
                .receivedBy(productCreateRequest.getReceivedBy())
                .dateReceived(productCreateRequest.getDateReceived())
                .quantity(productCreateRequest.getQuantity())
                .sid(productCreateRequest.getSid())
                .warehouseSid(productCreateRequest.getWarehouseSid())
                .sku(productCreateRequest.getSku())
                .brand(Brand.builder()
                               .sid(productCreateRequest.getBrand().getSid())
                               .name(productCreateRequest.getBrand().getName())
                               .build())
                .category(Category.builder()
                                  .name(productCreateRequest.getCategory().getName())
                                  .sid(productCreateRequest.getCategory().getSid())
                                  .build())
                .supplier(Supplier.builder()
                                  .sid(productCreateRequest.getSupplier().getSid())
                                  .email(productCreateRequest.getSupplier().getEmail())
                                  .phoneNumber(productCreateRequest.getSupplier().getPhoneNumber())
                                  .leadTime(productCreateRequest.getSupplier().getLeadTime())
                                  .name(productCreateRequest.getSupplier().getName())
                                  .build())
                .build();
    }

    private ProductCreateRequest buildProductCreateRequest() {

        return ProductCreateRequest.builder()
                .name(faker.funnyName().name())
                .sid(UUID.randomUUID())
                .price(faker.number().randomDouble(2, 1, 99999))
                .sku(faker.funnyName().name())
                .category(ProductCreateRequest.CategoryRequest.builder()
                                  .sid(UUID.randomUUID())
                                  .name(faker.name().name())
                                  .build())
                .brand(ProductCreateRequest.BrandRequest.builder()
                               .sid(UUID.randomUUID())
                               .name(faker.book().author())
                               .build())
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .sid(UUID.randomUUID())
                                  .email(faker.internet().emailAddress())
                                  .name(faker.company().name())
                                  .leadTime(faker.number().numberBetween(0, 30))
                                  .phoneNumber(faker.phoneNumber().phoneNumber())
                                  .build())
                .dateReceived(Instant.now())
                .receivedBy(faker.name().fullName())
                .quantity(faker.number().numberBetween(1, 99999))
                .warehouseSid(UUID.randomUUID())
                .discount(faker.number().randomDouble(2, 10, 60))
                .build();
    }
}
