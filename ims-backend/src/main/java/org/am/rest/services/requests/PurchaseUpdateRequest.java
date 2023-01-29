package org.am.rest.services.requests;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.am.library.entities.util.PurchaseStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Builder(builderClassName = "Builder")
public class PurchaseUpdateRequest {

    @NotNull
    int invoice;

    PurchaseStatus status;

    List<Integer> products;
}
