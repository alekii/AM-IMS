package org.am.domain.api;

import org.am.domain.catalog.Warehouse;

public interface UpdateWarehouseUseCase {

    Warehouse update(final Warehouse warehouse);
}
