package org.am.infrastructure.warehouses;

import org.am.infrastructure.warehouses.projections.WarehouseProjection;

public interface WarehouseQueryDslRepository {

    WarehouseProjection findByIdFetch(final int id);
}
