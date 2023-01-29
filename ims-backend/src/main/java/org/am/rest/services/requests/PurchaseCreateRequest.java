package org.am.rest.services.requests;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.am.library.entities.util.PurchaseStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Builder(builderClassName = "Builder")
public class PurchaseCreateRequest {

    @NotNull
    int invoice;

    @NotBlank
    @Size(max = 50)
    String receivedBy;

    PurchaseStatus status;

    UUID warehouseSid;

    UUID supplier;

    Instant dateReceived;

    Double totalAmount;

    List<Integer> products;
}
