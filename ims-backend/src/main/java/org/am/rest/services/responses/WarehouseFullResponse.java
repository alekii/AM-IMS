package org.am.rest.services.responses;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class WarehouseFullResponse {

    UUID sid;

    String name;

    String phoneNumber;

    String contactName;

    AddressResponse address;

    @Value
    @Builder
    @RequiredArgsConstructor
    public static class AddressResponse {

        String street;

        Double latitude;

        Double longitude;

        String mapUrl;

        TownResponse town;

        CountyResponse county;

        @Value
        @Builder
        @RequiredArgsConstructor
        public static class TownResponse {

            UUID sid;

            String name;
        }

        @Value
        @Builder
        @RequiredArgsConstructor
        public static class CountyResponse {

            UUID sid;

            String name;
        }
    }
}
