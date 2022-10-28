package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Warehouse;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.WarehouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class WarehouseToWarehouseEntityConverter
        implements Converter.WithTwoSources<Warehouse, AddressEntity, WarehouseEntity> {

    @Override
    @Mapping(target = "sid", source = "source.sid")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "phoneNumber", source = "source.phoneNumber")
    @Mapping(target = "contactName", source = "source.contactName")
    @Mapping(target = "createdAt", source = "source.createdAt")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "warehouseTownCoverages", ignore = true)
    @Mapping(target = "trackingNumbersCount", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    public abstract WarehouseEntity convert(Warehouse source, AddressEntity address);
}
