package org.am.rest.services.requests;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
public class WarehouseCreateRequest {

    @NotNull
    @Valid
    @Size(max = 45)
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
}
