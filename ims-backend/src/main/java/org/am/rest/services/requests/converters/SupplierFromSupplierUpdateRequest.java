package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SupplierFromSupplierUpdateRequest
        extends Converter.WithTwoSources<SupplierUpdateRequest, UUID, Supplier> {

    @Override
    @Mapping(source = "source", target = ".")
    @Mapping(source = "supplierSid", target = "sid")
    Supplier convert(SupplierUpdateRequest source, UUID supplierSid);
}
