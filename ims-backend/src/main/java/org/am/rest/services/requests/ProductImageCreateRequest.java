package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder(builderClassName = "Builder")
public class ProductImageCreateRequest {

    @NotNull
    UUID productSid;

    @NotBlank
    @Size(max = 255)
    String imagePath;

    public ProductImageCreateRequest() {

        super();
    }
}
