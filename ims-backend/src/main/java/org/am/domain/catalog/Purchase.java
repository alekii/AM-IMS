package org.am.domain.catalog;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.am.library.entities.util.PurchaseStatus;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder(builderClassName = "Builder")
public class Purchase {

    UUID sid;

    int invoice;

    PurchaseStatus status;

    UUID warehouseSid;

    Supplier supplier;

    Instant dateReceived;

    Double totalAmount;

    @Setter
    @Getter
    @lombok.Builder(builderClassName = "Builder")
    public static class WarehouseShortDetails {

        UUID warehouseSid;

        String name;

        String town;
    }
}
