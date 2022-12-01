package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Brand;
import org.am.library.entities.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandConverter extends Converter<BrandEntity, Brand> {

    @Override
    @Mapping(source = "source", target = ".")
    Brand convert(BrandEntity source);
}

