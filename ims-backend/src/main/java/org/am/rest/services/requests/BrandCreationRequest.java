package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Builder(builderClassName = "Builder")
public class BrandCreationRequest {

    @NotBlank
    @Size(max = 50)
    String name;
}
