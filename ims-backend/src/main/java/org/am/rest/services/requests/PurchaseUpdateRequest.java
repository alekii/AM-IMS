package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.am.domain.validation.annotations.EnumConstraint;
import org.am.library.entities.util.PurchaseStatus;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Builder(builderClassName = "Builder")
public class PurchaseUpdateRequest {

    @NotBlank
    @EnumConstraint(PurchaseStatus.class)
    String status;

    List<Integer> products;
}
