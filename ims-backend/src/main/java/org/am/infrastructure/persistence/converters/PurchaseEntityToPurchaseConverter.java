package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Purchase;
import org.am.library.entities.PurchaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseEntityToPurchaseConverter extends Converter<PurchaseEntity, Purchase> {

    @Override
    @Mapping(source = "sid", target = "sid")
    @Mapping(source = "source.invoiceNumber", target = "invoice")
    @Mapping(source = "source.supplier", target = "supplier")
    @Mapping(source = "source.dateReceived", target = "dateReceived")
    @Mapping(source = "source.billValue", target = "totalAmount")
    @Mapping(source = "source.status", target = "status")
    @Mapping(source = "source.warehouse", target = "warehouse")
    @Mapping(source = "source.warehouse.address.town.county", target = "warehouse.address.county")
    Purchase convert(PurchaseEntity source);
}
