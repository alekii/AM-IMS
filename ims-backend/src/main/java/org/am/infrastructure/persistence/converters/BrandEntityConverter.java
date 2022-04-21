package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Brand;
import org.am.library.entities.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandEntityConverter extends Converter<Brand, BrandEntity> {

    @Override
    @Mapping(source = "source", target = ".")
    BrandEntity convert(Brand source);
}
