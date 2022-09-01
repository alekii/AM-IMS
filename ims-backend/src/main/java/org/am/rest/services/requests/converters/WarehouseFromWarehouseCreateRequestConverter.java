package org.am.rest.services.requests.converters;

import lombok.RequiredArgsConstructor;
import org.am.commons.utils.Converter;
import org.am.domain.catalog.Warehouse;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WarehouseFromWarehouseCreateRequestConverter
        implements Converter<WarehouseCreateRequest, Warehouse> {

    private final WarehouseAddressFromWarehouseAddressRequestConverter warehouseAddressFromWarehouseAddressRequestConverter;

    @Override
    public Warehouse convert(WarehouseCreateRequest request) {

        return Warehouse.builder()
                .sid(UUID.randomUUID())
                .name(request.getContactName())
                .contactName(request.getContactName())
                .phoneNumber(request.getPhoneNumber())
                .address(warehouseAddressFromWarehouseAddressRequestConverter.convert(request.getAddress()))
                .build();
    }
}
