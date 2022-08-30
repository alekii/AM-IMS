package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.WarehouseCreation;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.WarehouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WarehouseCreationToWarehouseEntityConverter
        extends Converter.WithTwoSources<WarehouseCreation, AddressEntity, WarehouseEntity> {

    UUID sid = UUID.randomUUID();

    @Override
    @Mapping(target = "sid", source = "source.sid")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "phoneNumber", source = "source.phoneNumber")
    @Mapping(target = "contactName", source = "source.contactName")
    @Mapping(target = "address", source = "address")
    WarehouseEntity convert(WarehouseCreation source, AddressEntity address);
}
