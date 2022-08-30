package org.am.rest.services.requests;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Value
public class WarehouseAddressCreationRequest {

    @NotEmpty
    @Size(max = 255)
    String street;

    @NotEmpty
    @Size(max = 255)
    String mapUrl;

    @NotBlank
    Double latitude;

    @NotBlank
    Double longitude;

    @NotNull
    @Valid
    TownRequest town;

    @Value
    public static class TownRequest {

        @NotNull
        UUID sid;

        @NotNull
        @Valid
        CountyRequest county;

        @Value
        public static class CountyRequest {

            @NotNull
            UUID sid;
        }
    }
}
