package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Brand;
import org.am.library.entities.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandEntityConverter extends Converter<Brand, BrandEntity> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "sid", source = "source.sid")
    @Mapping(target = "name", source = "source.name")
    BrandEntity convert(Brand source);
}
