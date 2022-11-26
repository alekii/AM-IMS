package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Product;
import org.am.library.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductEntityToProductConverter
        extends Converter<ProductEntity, Product> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sid", source = "sid")
    @Mapping(source = "source.name", target = "name")
    @Mapping(source = "source.category", target = "category")
    @Mapping(source = "source.brand", target = "brand")
    @Mapping(source = "source.date_received", target = "dateReceived")
    @Mapping(source = "source.received_by", target = "receivedBy")
    @Mapping(source = "source.price", target = "price")
    @Mapping(source = "source.quantity", target = "quantity")
    @Mapping(source = "source.discount", target = "discount")
    @Mapping(source = "source.description", target = "description")
    @Mapping(source = "source.warehouseSid", target = "warehouseSid")
    @Mapping(source = "source.supplied_by", target = "supplier")
    @Mapping(source = "source.sku", target = "sku")
    Product convert(ProductEntity source);
}

