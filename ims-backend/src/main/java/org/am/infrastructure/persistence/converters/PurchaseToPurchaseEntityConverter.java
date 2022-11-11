package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Purchase;
import org.am.library.entities.PurchaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WarehouseConverter.class})
public interface PurchaseToPurchaseEntityConverter extends Converter<Purchase, PurchaseEntity> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplier.id", ignore = true)
    @Mapping(source = "source.invoice", target = "invoiceNumber")
    @Mapping(source = "source.supplier", target = "supplier")
    @Mapping(target = "supplier.products", ignore = true)
    @Mapping(target = "supplier.purchases", ignore = true)
    @Mapping(source = "sid", target = "sid")
    @Mapping(target = "lineItems", ignore = true)
    @Mapping(source = "source.dateReceived", target = "dateReceived")
    @Mapping(source = "source.totalAmount", target = "billValue")
    @Mapping(source = "source.status", target = "status")
    PurchaseEntity convert(Purchase source);
}
