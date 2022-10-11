package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Supplier;
import org.am.library.entities.SupplierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplierToSupplierEntityConverter
        extends Converter<Supplier, SupplierEntity> {

    @Override
    @Mapping(target = "sid", source = "source.sid")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "phoneNumber", source = "source.phoneNumber")
    @Mapping(target = "email", source = "source.email")
    SupplierEntity convert(Supplier source);
}
