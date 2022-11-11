package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Purchase;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.PurchaseEntityToPurchaseConverter;
import org.am.infrastructure.persistence.converters.PurchaseEntityToPurchaseConverterImpl;
import org.am.infrastructure.persistence.converters.SupplierConverter;
import org.am.infrastructure.persistence.converters.SupplierConverterImpl;
import org.am.infrastructure.persistence.converters.WarehouseConverter;
import org.am.infrastructure.persistence.converters.WarehouseConverterImpl;
import org.am.library.entities.PurchaseEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PurchaseEntityToPurchaseConverterTest {

    private final Faker faker = new Faker();

    private final WarehouseConverter warehouseConverter = new WarehouseConverterImpl();

    private final SupplierConverter supplierConverter = new SupplierConverterImpl();

    private final PurchaseEntityToPurchaseConverter subject = new PurchaseEntityToPurchaseConverterImpl();

    @Test
    void convert_returnsAPurchase() {

        // Given
        final PurchaseEntity entity = faker.entity.purchase().build();

        final Purchase expected = buildPurchase(entity);

        // When
        final Purchase purchase = subject.convert(entity);

        // Then
        assertThat(purchase).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private Purchase buildPurchase(PurchaseEntity entity) {

        return Purchase.builder()
                .sid(entity.getSid())
                .totalAmount(entity.getBillValue())
                .invoice(entity.getInvoiceNumber())
                .dateReceived(entity.getDateReceived())
                .status(entity.getStatus())
                .warehouse(warehouseConverter.convert(entity.getWarehouse()))
                .supplier(supplierConverter.convert(entity.getSupplier()))
                .build();
    }
}
