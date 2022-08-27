package org.am.domain.api;

import org.am.domain.catalog.Warehouse;

import java.util.List;
import java.util.UUID;

public interface GetWarehouseUseCase {

    List<Warehouse> getWarehouses();

    Warehouse getBySid(UUID warehouseSid);
}
