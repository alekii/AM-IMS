package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.ProductImage;
import org.am.library.entities.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageEntityConverter extends Converter<ProductImage, ImageEntity> {

    @Override
    @Mapping(target = "sid", source = "source.sid")
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "imagePath", source = "source.imagePath")
    @Mapping(target = "product", ignore = true)
    ImageEntity convert(ProductImage source);
}
