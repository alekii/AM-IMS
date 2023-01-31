package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.fakers.Faker;
import org.am.rest.services.responses.PurchaseResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseResponseConverterTest {

    private final Faker faker = new Faker();

    private final PurchaseResponseConverter purchaseResponseConverter = new PurchaseResponseConverter();

    @Test
    void convert_aPurchase_toPurchaseResponse() {

        // Given
        final Purchase purchase = faker.domain.purchase().build();

        // When
        final PurchaseResponse purchaseResponse = purchaseResponseConverter.convert(purchase);

        // Then
        assertThat(purchaseResponse)
                .isEqualTo(PurchaseResponse.builder()
                                   .sid(purchase.getSid())
                                   .invoice(purchase.getInvoice())
                                   .totalAmount(purchase.getTotalAmount())
                                   .dateReceived(purchase.getDateReceived())
                                   .supplierResponse(buildSupplierResponse(purchase.getSupplier()))
                                   .productResponse(buildProductResponse(purchase.getProducts()))
                                   .build());
    }

    private List<PurchaseResponse.ProductResponse> buildProductResponse(List<Product> products) {

        return products.stream()
                .map(product -> PurchaseResponse.ProductResponse.builder()
                        .sid(product.getSid())
                        .price(product.getPrice())
                        .name(product.getName())
                        .build())
                .collect(Collectors.toList());
    }

    private PurchaseResponse.SupplierResponse buildSupplierResponse(Supplier supplier) {

        return PurchaseResponse.SupplierResponse.builder()
                .name(supplier.getName())
                .sid(supplier.getSid())
                .build();
    }
}
