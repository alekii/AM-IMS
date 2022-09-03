package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(builderClassName = "Builder")
public class WarehouseCreateRequest {

    String warehouseName;

    String phoneNumber;

    String contactName;

    WarehouseAddressCreationRequest address;

    public WarehouseCreateRequest() {

        super();
    }
}
