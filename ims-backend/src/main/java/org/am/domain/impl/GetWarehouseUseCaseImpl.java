package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetWarehouseUseCase;
import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.WarehouseNotFoundException;
import org.am.infrastructure.persistence.api.WarehouseDAO;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetWarehouseUseCaseImpl implements GetWarehouseUseCase {

    private final WarehouseDAO warehouseDAO;

    @Override
    public List<Warehouse> getWarehouses() {

        return warehouseDAO.findAll();
    }

    @Override
    public Warehouse getBySid(UUID warehouseSid) {

        return warehouseDAO.findBySid(warehouseSid)
                .orElseThrow(() -> WarehouseNotFoundException.forSid(warehouseSid));
    }
}
