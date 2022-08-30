package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;

public class WarehouseAddressFromWarehouseAddressRequestConverter
        implements Converter<WarehouseAddressCreationRequest, Address> {

    public Address convert(WarehouseAddressCreationRequest request) {

        return Address.builder()
                .mapUrl(request.getMapUrl())
                .street(request.getStreet())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .town(convertTown(request.getTown()))
                .build();
    }

    private Town convertTown(WarehouseAddressCreationRequest.TownRequest town) {

        return Town.builder()
                .sid(town.getSid())
                .county(convertCounty(town.getCounty()))
                .build();
    }

    private County convertCounty(WarehouseAddressCreationRequest.TownRequest.CountyRequest county) {

        return County.builder()
                .sid(county.getSid())
                .build();
    }
}
