package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.am.domain.validation.validators.constants.ValidationConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static org.am.domain.validation.validators.constants.ValidationConstants.EMAIL_MAX_LENGTH;

@AllArgsConstructor
@Getter
@Builder(builderClassName = "Builder")
public class SupplierUpdateRequest {

    @NotBlank
    @Size(max = 85)
    String name;

    @NotBlank
    @Pattern(regexp = ValidationConstants.EMAIL_ADDRESS_REGEX, message = ValidationConstants.INVALID_EMAIL)
    @Size(max = EMAIL_MAX_LENGTH)
    String email;

    @NotBlank
    String phoneNumber;

    @NotBlank
    @Size(max = 2)
    int leadTime;

    public SupplierUpdateRequest() {

        super();
    }
}
