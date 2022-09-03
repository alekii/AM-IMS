package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class WarehouseAddressCreationRequest {

    String street;

    String mapUrl;

    Double latitude;

    Double longitude;

    TownRequest town;

    @lombok.Builder(builderClassName = "Builder")
    @Getter
    @AllArgsConstructor
    public static class TownRequest {

        UUID sid;

        CountyRequest county;

        public TownRequest() {

            super();
        }

        @Getter
        @AllArgsConstructor
        @lombok.Builder(builderClassName = "Builder")
        public static class CountyRequest {

            UUID sid;

            public CountyRequest() {

                super();
            }
        }
    }

    public WarehouseAddressCreationRequest() {

        super();
    }
}
