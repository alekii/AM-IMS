package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.rest.services.requests.WarehouseUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WarehouseFromWarehouseUpdateRequestConverter implements Converter.WithTwoSources<WarehouseUpdateRequest, UUID, Warehouse> {

    @Override
    public Warehouse convert(WarehouseUpdateRequest request, UUID warehouseSid) {

        return Warehouse.builder()
                .sid(warehouseSid)
                .name(request.getWarehouseName())
                .contactName(request.getContactName())
                .phoneNumber(request.getPhoneNumber())
                .address(Address.builder()
                                 .street(request.getAddress().getStreet())
                                 .mapUrl(request.getAddress().getMapUrl())
                                 .latitude(request.getAddress().getLatitude())
                                 .longitude(request.getAddress().getLongitude())
                                 .town(Town.builder()
                                               .sid(request.getAddress().getTown().getTownSid())
                                               .build()).build())
                .build();
    }
}
