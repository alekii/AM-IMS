package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.am.domain.validation.annotations.UUIDPattern;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class WarehouseAddressCreationRequest {

    @NotEmpty
    @Size(max = 255)
    String street;

    @Size(max = 255)
    String mapUrl;

    Double latitude;

    Double longitude;

    @NotNull
    @Valid
    TownRequest town;

    @NotNull
    @Valid
    CountyRequest county;

    @lombok.Builder(builderClassName = "Builder")
    @Getter
    @AllArgsConstructor
    public static class TownRequest {

        @UUIDPattern
        UUID townSid;

        public TownRequest() {

            super();
        }
    }

    @Getter
    @AllArgsConstructor
    @lombok.Builder(builderClassName = "Builder")
    public static class CountyRequest {

        @UUIDPattern
        UUID countySid;

        public CountyRequest() {

            super();
        }
    }

    public WarehouseAddressCreationRequest() {

        super();
    }
}
