package org.am.rest.services.requests.converters;

import org.am.domain.catalog.Address;
import org.am.domain.catalog.County;
import org.am.domain.catalog.Town;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;
import org.springframework.stereotype.Component;

@Component
public class WarehouseAddressFromWarehouseAddressRequestConverter {

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
                .sid(town.getTownSid())
                .build();
    }

    private County convertCounty(WarehouseAddressCreationRequest.CountyRequest county) {

        return County.builder()
                .sid(county.getCountySid())
                .build();
    }
}
