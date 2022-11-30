package org.am.persistence.jpa.converters;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.Purchase;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.converters.AddressToAddressEntityConverter;
import org.am.infrastructure.persistence.converters.AddressToAddressEntityConverterImpl;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverterImpl;
import org.am.infrastructure.persistence.converters.SupplierToSupplierEntityConverter;
import org.am.infrastructure.persistence.converters.SupplierToSupplierEntityConverterImpl;
import org.am.infrastructure.persistence.converters.WarehouseToWarehouseEntityConverter;
import org.am.infrastructure.persistence.converters.WarehouseToWarehouseEntityConverterImpl;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.CountyEntity;
import org.am.library.entities.PurchaseEntity;
import org.am.library.entities.TownEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PurchaseToPurchaseEntityConverterTest {

    private final Faker faker = new Faker();

    private final AddressToAddressEntityConverter addressConverter = new AddressToAddressEntityConverterImpl();

    private final WarehouseToWarehouseEntityConverter warehouseConverter = new WarehouseToWarehouseEntityConverterImpl();

    private final SupplierToSupplierEntityConverter supplierEntityConverter = new SupplierToSupplierEntityConverterImpl();

    private final PurchaseToPurchaseEntityConverter subject = new PurchaseToPurchaseEntityConverterImpl();

    @Test
    void convert_returnsAPurchaseEntity() {

        // Given
        final Purchase purchase = faker.domain.purchase().build();

        final PurchaseEntity purchaseEntity = buildPurchaseEntity(purchase);

        // When
        final PurchaseEntity convertedPurchaseEntity = subject.convert(purchase);

        // Then
        Assertions.assertThat(convertedPurchaseEntity.getSid()).isInstanceOf(UUID.class);
        assertThat(convertedPurchaseEntity)
                .usingRecursiveComparison()
                //  .ignoringFields("warehouse.address.town")
                .isEqualTo(purchaseEntity);
    }

    private PurchaseEntity buildPurchaseEntity(Purchase purchase) {

        return PurchaseEntity.builder()
                .invoiceNumber(purchase.getInvoice())
                .sid(purchase.getSid())
                .warehouseSid(purchase.getWarehouseSid())
                .supplier(supplierEntityConverter.convert(purchase.getSupplier()))
                .dateReceived(purchase.getDateReceived())
                .status(purchase.getStatus())
                .billValue(purchase.getTotalAmount())
                .build();
    }

    private AddressEntity buildAddressEntity(final Address address) {

        TownEntity townEntity = TownEntity.builder()
                .sid(address.getTown().getSid())
                .name(address.getTown().getName())
                .county(CountyEntity.builder()
                                .name(address.getCounty().getName())
                                .sid(address.getCounty().getSid())
                                .build())
                .build();

        return addressConverter.convert(address, townEntity);
    }
}
