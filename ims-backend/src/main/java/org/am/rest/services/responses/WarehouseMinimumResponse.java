package org.am.rest.services.responses;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class WarehouseMinimumResponse {

    UUID sid;

    String name;

    AddressResponse address;

    @Value
    @Builder
    public static class AddressResponse {

        String street;

        String town;

        String county;
    }
}
