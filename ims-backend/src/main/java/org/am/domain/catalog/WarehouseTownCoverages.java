package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class WarehouseTownCoverages {

    Integer warehouseId;

    Integer townID;
}
