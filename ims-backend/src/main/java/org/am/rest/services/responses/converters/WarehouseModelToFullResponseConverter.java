package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.domain.catalog.Warehouse;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WarehouseModelToFullResponseConverter {

    public WarehouseFullResponse convert(final Warehouse warehouse) {

        return WarehouseFullResponse.builder()
                .sid(warehouse.getSid())
                .name(warehouse.getName())

                .phoneNumber(Optional.ofNullable(warehouse)
                                     .map(Warehouse::getPhoneNumber)
                                     .orElse(null))

                .contactName(Optional.ofNullable(warehouse)
                                     .map(Warehouse::getContactName)
                                     .orElse(null))

                .address(buildAddressResponse(warehouse.getAddress()))
                .build();
    }

    private WarehouseFullResponse.AddressResponse buildAddressResponse(Address address) {

        return WarehouseFullResponse.AddressResponse.builder()

                .street(Optional.ofNullable(address)
                                .map(Address::getStreet)
                                .orElse(null))

                .latitude(Optional.ofNullable(address)
                                  .map(Address::getLatitude)
                                  .orElse(null))

                .longitude(Optional.ofNullable(address)
                                   .map(Address::getLongitude)
                                   .orElse(null))

                .mapUrl(Optional.ofNullable(address)
                                .map(Address::getMapUrl)
                                .orElse(null))

                .town(WarehouseFullResponse.AddressResponse.TownResponse.builder()
                              .name(Optional.ofNullable(address)
                                            .map(Address::getTown)
                                            .map(Town::getName)
                                            .orElse(null))
                              .sid(Optional.ofNullable(address)
                                           .map(Address::getTown)
                                           .map(Town::getSid)
                                           .orElse(null))
                              .county(WarehouseFullResponse.AddressResponse.TownResponse.CountyResponse.builder()
                                              .sid(Optional.ofNullable(address)
                                                           .map(Address::getTown)
                                                           .map(Town::getCounty)
                                                           .map(County::getSid)
                                                           .orElse(null))
                                              .name(Optional.ofNullable(address)
                                                            .map(Address::getTown)
                                                            .map(Town::getCounty)
                                                            .map(County::getName)
                                                            .orElse(null))
                                              .build())
                              .build())
                .build();
    }
}
