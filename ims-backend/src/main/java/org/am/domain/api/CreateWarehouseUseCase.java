package org.am.domain.api;

import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.WarehouseCreation;

public interface CreateWarehouseUseCase {

    Warehouse create(WarehouseCreation warehouseCreation);
}
