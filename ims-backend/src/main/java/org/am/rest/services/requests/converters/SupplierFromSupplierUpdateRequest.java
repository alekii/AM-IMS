package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplierFromSupplierUpdateRequest
        extends Converter<SupplierUpdateRequest, Supplier> {

    @Override
    @Mapping(source = "source.leadTime", target = "leadTime")
    @Mapping(source = "source.name", target = "name")
    @Mapping(source = "source.email", target = "email")
    @Mapping(source = "source.phoneNumber", target = "phoneNumber")
    @Mapping(source = "source.sid", target = "sid")
    Supplier convert(SupplierUpdateRequest source);
}
