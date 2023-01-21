package org.am.rest.services.requests;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Value
public class ProductImageCreateRequest {

    @NotNull
    UUID productSid;

    @NotBlank
    @Size(max = 255)
    String imagePath;
}
