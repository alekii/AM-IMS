package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Builder(builderClassName = "Builder")
public class PurchaseCreateRequest {

    @Min(1)
    int invoice;

    @NotBlank
    @Size(max = 50)
    String receivedBy;

    UUID warehouseSid;

    UUID supplier;
     
    @NotEmpty
    @Size(min = 1, max = 10000)
    List<@NotNull Integer> products;
}
