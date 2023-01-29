package org.am.rest.services.requests.converters;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.fakers.Faker;
import org.am.library.entities.util.PurchaseStatus;
import org.am.rest.services.requests.PurchaseCreateRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseFromPurchaseCreateRequestConverterTest {

    private final Faker faker = new Faker();

    private final PurchaseFromPurchaseCreateRequestConverter subject = new PurchaseFromPurchaseCreateRequestConverter();

    @Test
    void convert() {

        // Given
        final PurchaseCreateRequest request = faker.domain.purchaseCreateRequest().build();

        // When
        final Purchase purchase = subject.convert(request);

        // Then
        assertThat(purchase)
                .usingRecursiveComparison()
                .ignoringFields("sid")
                .isEqualTo(Purchase.builder()
                                   .status(PurchaseStatus.PENDING_APPROVAL)
                                   .warehouseSid(request.getWarehouseSid())
                                   .invoice(request.getInvoice())
                                   .supplier(buildSupplier(request.getSupplier()))
                                   .dateReceived(request.getDateReceived())
                                   .totalAmount(request.getTotalAmount())
                                   .products(buildProducts(request.getProducts()))
                                   .build());
    }

    @Test
    void convert_whenSupplierSidIsNull_supplierIsNull() {

        // Given
        final PurchaseCreateRequest request = faker.domain.purchaseCreateRequest()
                .supplier(null)
                .build();

        // When
        final Purchase purchase = subject.convert(request);

        // Then
        assertThat(purchase.getSupplier().getSid())
                .isNull();
    }

    private Supplier buildSupplier(UUID supplier) {

        return Supplier.builder()
                .sid(supplier)
                .build();
    }

    private List<Product> buildProducts(List<Integer> products) {

        return products.stream()
                .map(product -> Product.builder()
                        .id(product)
                        .build())
                .collect(Collectors.toList());
    }
}
