package org.am.persistence.jpa.converters;

import org.am.domain.catalog.LineItem;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.LineItemToLineItemEntityConverter;
import org.am.infrastructure.persistence.converters.ProductToProductEntityConverter;
import org.am.infrastructure.persistence.converters.ProductToProductEntityConverterImpl;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverterImpl;
import org.am.library.entities.PurchaseProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LineItemToLineItemEntityConverterTest {

    private final Faker faker = new Faker();

    private final ProductToProductEntityConverter productToProductEntityConverter = new ProductToProductEntityConverterImpl();

    private final ProductToProductEntityConverter productConverter = new ProductToProductEntityConverterImpl();

    private final PurchaseToPurchaseEntityConverter purchaseConverter = new PurchaseToPurchaseEntityConverterImpl();

    private final LineItemToLineItemEntityConverter subject = new LineItemToLineItemEntityConverter(productConverter, purchaseConverter);

    @Test
    void convert_returnsLineItemConverter() {

        // Given
        final LineItem lineItem = faker.domain.lineItem().build();

        final PurchaseProductEntity lineItemEntity = buildLineItemEntity(lineItem);

        // When
        final PurchaseProductEntity convertedLineItem = subject.convert(lineItem);

        // Then
        Assertions.assertThat(convertedLineItem.getSid()).isInstanceOf(UUID.class);
        assertThat(convertedLineItem)
                .usingRecursiveComparison()
                .ignoringFields("product.sid")
                .isEqualTo(lineItemEntity);
    }

    private PurchaseProductEntity buildLineItemEntity(LineItem lineItem) {

        return PurchaseProductEntity.builder()
                .sid(lineItem.getSid())
                .price(lineItem.getPrice())
                .quantity(lineItem.getQuantity())
                .product(productToProductEntityConverter.convert(lineItem.getProduct()))
                .purchase(purchaseConverter.convert(lineItem.getPurchase()))
                .build();
    }
}
