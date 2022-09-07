package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Builder(builderClassName = "Builder")
public class WarehouseCreateRequest {

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
    WarehouseAddressCreationRequest address;

    public WarehouseCreateRequest() {

        super();
    }
}
