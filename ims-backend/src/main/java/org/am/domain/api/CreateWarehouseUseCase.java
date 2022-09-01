package org.am.domain.api;

import org.am.domain.catalog.Warehouse;

public interface CreateWarehouseUseCase {

    Warehouse create(Warehouse warehouse);
}
