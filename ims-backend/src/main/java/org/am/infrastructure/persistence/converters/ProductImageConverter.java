package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.ProductImage;
import org.am.library.entities.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductImageConverter extends Converter.WithTwoSources<ImageEntity, UUID, ProductImage> {

    @Override
    @Mapping(source = "source.sid", target = "sid")
    @Mapping(source = "source.id", target = "id")
    @Mapping(source = "source.imagePath", target = "imagePath")
    @Mapping(source = "productSid", target = "productSid")
    ProductImage convert(ImageEntity source, UUID productSid);
}
