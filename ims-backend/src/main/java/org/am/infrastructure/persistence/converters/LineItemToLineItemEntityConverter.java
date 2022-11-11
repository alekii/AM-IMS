package org.am.infrastructure.persistence.converters;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.LineItem;
import org.am.library.entities.PurchaseProductEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineItemToLineItemEntityConverter {

    private final ProductToProductEntityConverter productConverter;

    private final PurchaseToPurchaseEntityConverter purchaseConverter;

    public PurchaseProductEntity convert(final LineItem lineItem) {

        return PurchaseProductEntity.builder()
                .sid(lineItem.getSid())
                .price(lineItem.getPrice())
                .product(productConverter.convert(lineItem.getProduct()))
                .purchase(purchaseConverter.convert(lineItem.getPurchase()))
                .quantity(lineItem.getQuantity())
                .build();
    }
}
