package org.am.rest.services.responses.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.responses.SupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SupplierResponseConverter extends Converter<Supplier, SupplierResponse> {

    SupplierResponseConverter INSTANCE = Mappers.getMapper(SupplierResponseConverter.class);

    @Override
    SupplierResponse convert(Supplier source);
}
