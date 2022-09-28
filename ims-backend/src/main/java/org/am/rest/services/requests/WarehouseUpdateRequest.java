package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.am.domain.validators.UUIDPattern;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder(builderClassName = "Builder")
public class WarehouseUpdateRequest {

    @NotBlank
    @Size(max = 50)
    String warehouseName;

    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "^\\+[0-9]*$")
    String phoneNumber;

    @NotBlank
    @Size(max = 255)
    String contactName;

    @NotNull
    @Valid
    WarehouseAddressUpdateRequest address;

    @Getter
    @AllArgsConstructor
    @lombok.Builder(builderClassName = "Builder")
    public static class WarehouseAddressUpdateRequest {

        public WarehouseAddressUpdateRequest() {

            super();
        }

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
    }

    public WarehouseUpdateRequest() {

        super();
    }
}
