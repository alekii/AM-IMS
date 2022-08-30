package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.Warehouse;
import org.am.rest.services.responses.WarehouseMinimumResponse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseModelToMinimumResponseConverter {

    public WarehouseMinimumResponse convert(final Warehouse warehouse) {

        return WarehouseMinimumResponse.builder()
                .sid(warehouse.getSid())
                .name(warehouse.getName())
                .address(buildAddress(warehouse.getAddress()))
                .build();
    }

    private WarehouseMinimumResponse.AddressResponse buildAddress(Address address) {

        return WarehouseMinimumResponse.AddressResponse.builder()
                .street(address.getStreet())
                .town(address.getTown().getName())
                .county(address.getTown().getCounty().getName())
                .build();
    }
}
