package org.am.rest.services.responses;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@RequiredArgsConstructor
public class WarehouseMinimumResponse {

    UUID sid;

    String name;

    AddressResponse address;

    @Value
    @Builder
    @RequiredArgsConstructor
    public static class AddressResponse {

        String street;

        String town;

        String county;
    }
}
