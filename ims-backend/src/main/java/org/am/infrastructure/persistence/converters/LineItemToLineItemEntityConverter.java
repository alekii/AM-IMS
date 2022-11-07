package org.am.infrastructure.persistence.converters;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.LineItem;
import org.am.library.entities.PurchaseProductEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LineItemToLineItemEntityConverter {

    private final ProductToProductEntityConverter productConverter;

    private final PurchaseToPurchaseEntityConverter purchaseConverter;

    public PurchaseProductEntity convert(final LineItem lineItem) {

        return PurchaseProductEntity.builder()
                .sid(UUID.randomUUID())
                .price(lineItem.getPrice())
                .product(productConverter.convert(lineItem.getProduct()))
                .purchases(purchaseConverter.convert(lineItem.getPurchase()))
                .quantity(lineItem.getQuantity())
                .build();
    }
}
