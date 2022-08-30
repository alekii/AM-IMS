package org.am.rest.services.requests.converters;

import lombok.RequiredArgsConstructor;
import org.am.commons.utils.Converter;
import org.am.domain.catalog.WarehouseCreation;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseCreationFromCreateRequestConverter
        implements Converter<WarehouseCreateRequest, WarehouseCreation> {

    private final WarehouseAddressFromWarehouseAddressRequestConverter warehouseAddressFromWarehouseAddressRequestConverter;

    @Override
    public WarehouseCreation convert(WarehouseCreateRequest request) {

        return WarehouseCreation.builder()
                .name(request.getContactName())
                .contactName(request.getContactName())
                .phoneNumber(request.getPhoneNumber())
                .address(warehouseAddressFromWarehouseAddressRequestConverter.convert(request.getAddress()))
                .build();
    }
}
