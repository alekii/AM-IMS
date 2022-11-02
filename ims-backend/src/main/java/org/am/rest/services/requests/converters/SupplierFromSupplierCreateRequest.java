package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.commons.utils.MappingUtil;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.requests.SupplierCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplierFromSupplierCreateRequest
        extends Converter<SupplierCreateRequest, Supplier> {

    @Override
    @Mapping(source = "source.leadTime", target = "leadTime")
    @Mapping(source = "source.name", target = "name")
    @Mapping(source = "source.email", target = "email")
    @Mapping(source = "source.phoneNumber", target = "phoneNumber")
    @Mapping(target = "sid", expression = MappingUtil.GENERATE_RANDOM_UUID)
    Supplier convert(SupplierCreateRequest source);
}
