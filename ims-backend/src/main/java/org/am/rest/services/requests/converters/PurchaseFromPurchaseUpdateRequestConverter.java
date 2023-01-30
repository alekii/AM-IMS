package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.library.entities.util.PurchaseStatus;
import org.am.rest.services.requests.PurchaseUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PurchaseFromPurchaseUpdateRequestConverter
        implements Converter.WithTwoSources<PurchaseUpdateRequest, UUID, Purchase> {

    @Override
    public Purchase convert(PurchaseUpdateRequest source, UUID purchaseSid) {

        return Purchase.builder()
                .sid(purchaseSid)
                .products(convertProducts(source.getProducts()))
                .status(PurchaseStatus.valueOf(source.getStatus()))
                .build();
    }

    private List<Product> convertProducts(List<Integer> products) {

        return products.stream()
                .map(product -> Product.builder().id(product).build())
                .collect(Collectors.toList());
    }
}
