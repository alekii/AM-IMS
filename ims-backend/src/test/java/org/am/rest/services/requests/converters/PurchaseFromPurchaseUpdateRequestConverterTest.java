package org.am.rest.services.requests.converters;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.fakers.Faker;
import org.am.library.entities.util.PurchaseStatus;
import org.am.rest.services.requests.PurchaseUpdateRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseFromPurchaseUpdateRequestConverterTest {

    private final Faker faker = new Faker();

    private final PurchaseFromPurchaseUpdateRequestConverter subject = new PurchaseFromPurchaseUpdateRequestConverter();

    @Test
    void convert_returnsPurchase_FromPurchaseUpdateRequest() {

        // Given
        final UUID purchaseSid = UUID.randomUUID();
        final PurchaseUpdateRequest purchaseUpdateRequest = faker.domain.purchaseUpdateRequest().build();
        final Purchase purchase = buildPurchase(purchaseUpdateRequest, purchaseSid);

        // When
        final Purchase updatedPurchase = subject.convert(purchaseUpdateRequest, purchaseSid);

        // Then
        assertThat(updatedPurchase).usingRecursiveComparison().isEqualTo(purchase);
    }

    private Purchase buildPurchase(PurchaseUpdateRequest purchaseUpdateRequest, UUID purchaseSid) {

        return Purchase
                .builder()
                .sid(purchaseSid)
                .status(PurchaseStatus.valueOf(purchaseUpdateRequest.getStatus()))
                .products(buildProducts(purchaseUpdateRequest.getProducts()))
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
