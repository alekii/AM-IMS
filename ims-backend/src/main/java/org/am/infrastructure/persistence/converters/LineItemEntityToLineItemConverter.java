package org.am.infrastructure.persistence.converters;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.LineItem;
import org.am.library.entities.PurchaseProductEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LineItemEntityToLineItemConverter {

    private final ProductEntityToProductConverter productEntityConverter;

    private final PurchaseEntityToPurchaseConverter purchaseEntityConverter;

    public LineItem convert(final PurchaseProductEntity lineItemsEntity) {

        return LineItem.builder()
                .sid(UUID.randomUUID())
                .price(lineItemsEntity.getPrice())
                .product(productEntityConverter.convert(lineItemsEntity.getProduct()))
                .purchase(purchaseEntityConverter.convert(lineItemsEntity.getPurchases()))
                .quantity(lineItemsEntity.getQuantity())
                .build();
    }
}
