package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Category;
import org.am.library.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryConverter extends Converter<CategoryEntity, Category> {

    @Override
    @Mapping(target = "sid", source = "source.sid")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "products", ignore = true)
    Category convert(CategoryEntity source);
}
