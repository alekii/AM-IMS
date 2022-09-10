package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Warehouse;
import org.am.library.entities.WarehouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarehouseConverter
        extends Converter<WarehouseEntity, Warehouse> {

    @Override
    @Mapping(target = "sid", source = "source.sid")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "phoneNumber", source = "source.phoneNumber")
    @Mapping(target = "contactName", source = "source.contactName")
    @Mapping(target = "address", source = "source.address")
    @Mapping(target = "address.county", source = "source.address.town.county")
    Warehouse convert(WarehouseEntity source);
}
