package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class CategoryCreationRequest {

    @NotBlank
    @Size(max = 50)
    String name;
}
