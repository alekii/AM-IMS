package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Category;
import org.am.library.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryEntityConverter extends Converter<Category, CategoryEntity> {

    @Override
    @Mapping(target = "sid", source = "source.sid")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    CategoryEntity convert(Category source);
}
