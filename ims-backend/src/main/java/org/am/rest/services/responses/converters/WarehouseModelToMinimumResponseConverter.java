package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.rest.services.responses.WarehouseMinimumResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WarehouseModelToMinimumResponseConverter {

    public WarehouseMinimumResponse convert(final Warehouse warehouse) {

        return WarehouseMinimumResponse.builder()
                .sid(warehouse.getSid())

                .name(Optional.ofNullable(warehouse)
                              .map(Warehouse::getName)
                              .orElse(null))

                .address(buildAddress(warehouse.getAddress()))
                .build();
    }

    private WarehouseMinimumResponse.AddressResponse buildAddress(Address address) {

        return WarehouseMinimumResponse.AddressResponse.builder()
                .street(Optional.ofNullable(address)
                                .map(Address::getStreet)
                                .orElse(null))

                .town(Optional.ofNullable(address)
                              .map(Address::getTown)
                              .map(Town::getName)
                              .orElse(null))

                .county(Optional.ofNullable(address)
                                .map(Address::getTown)
                                .map(Town::getCounty)
                                .map(County::getName)
                                .orElse(null))
                .build();
    }
}
