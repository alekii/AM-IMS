package org.am.infrastructure.products.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.ProductSearchResultModel;
import org.am.infrastructure.products.dto.ProductSearchResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductSearchModelConverter extends Converter<ProductSearchResult, ProductSearchResultModel> {

    @Override
    @Mapping(source = "source", target = ".")
    ProductSearchResultModel convert(ProductSearchResult source);
}
