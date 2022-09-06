package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;
import org.springframework.stereotype.Component;

@Component
public class WarehouseAddressFromWarehouseAddressRequestConverter
        implements Converter<WarehouseAddressCreationRequest, Address> {

    public Address convert(WarehouseAddressCreationRequest request) {

        return Address.builder()
                .mapUrl(request.getMapUrl())
                .street(request.getStreet())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .town(convertTown(request.getTown()))
                .county(convertCounty(request.getCounty()))
                .build();
    }

    private Town convertTown(WarehouseAddressCreationRequest.TownRequest town) {

        return Town.builder()
                .sid(town.getSid())
                .build();
    }

    private County convertCounty(WarehouseAddressCreationRequest.CountyRequest county) {

        return County.builder()
                .sid(county.getSid())
                .build();
    }
}
