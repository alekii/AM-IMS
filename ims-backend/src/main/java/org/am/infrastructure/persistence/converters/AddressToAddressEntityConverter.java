package org.am.infrastructure.persistence.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Address;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.TownEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressToAddressEntityConverter extends Converter.WithTwoSources<Address, TownEntity, AddressEntity> {

    @Override
    @Mapping(target = "street", source = "source.street")
    @Mapping(target = "mapUrl", source = "source.mapUrl")
    @Mapping(target = "longitude", source = "source.longitude")
    @Mapping(target = "latitude", source = "source.latitude")
    @Mapping(target = "town", source = "town")
    AddressEntity convert(Address source, TownEntity town);
}
