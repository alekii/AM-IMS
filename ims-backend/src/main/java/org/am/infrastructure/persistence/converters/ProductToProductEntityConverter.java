package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.commons.utils.MappingUtil;
import org.am.domain.catalog.Product;
import org.am.library.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductToProductEntityConverter
        extends Converter<Product, ProductEntity> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sid", expression = MappingUtil.GENERATE_RANDOM_UUID)
    @Mapping(source = "source.name", target = "name")
    @Mapping(source = "source.category", target = "category")
    @Mapping(source = "source.brand", target = "brand")
    @Mapping(target = "brand.id", ignore = true)
    @Mapping(target = "brand.products", ignore = true)
    @Mapping(target = "category.products", ignore = true)
    @Mapping(source = "source.dateReceived", target = "date_received")
    @Mapping(source = "source.receivedBy", target = "received_by")
    @Mapping(source = "source.price", target = "price")
    @Mapping(source = "source.quantity", target = "quantity")
    @Mapping(source = "source.discount", target = "discount")
    @Mapping(source = "source.description", target = "description")
    @Mapping(source = "source.warehouseSid", target = "warehouseSid")
    @Mapping(source = "source.supplier", target = "supplied_by")
    @Mapping(target = "supplied_by.products", ignore = true)
    @Mapping(target = "supplied_by.purchases", ignore = true)
    @Mapping(target = "supplied_by.id", ignore = true)
    @Mapping(source = "source.sku", target = "sku")
    @Mapping(target = "category.id", ignore = true)
    @Mapping(target = "lineItems", ignore = true)
    @Mapping(target = "images", ignore = true)
    ProductEntity convert(Product source);
}
