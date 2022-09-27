package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.UpdateWarehouseUseCase;
import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.NotFound.WarehouseNotFoundException;
import org.am.domain.validators.WarehouseValidator;
import org.am.infrastructure.persistence.api.WarehouseDAO;

@RequiredArgsConstructor
public class UpdateWarehouseUseCaseImpl implements UpdateWarehouseUseCase {

    private final WarehouseDAO warehouseDAO;

    private final WarehouseValidator warehouseValidator;

    @Override
    public Warehouse update(final Warehouse warehouse) {

        getWarehouseToUpdate(warehouse);
        warehouseValidator.validatePhoneNumber(warehouse);
        return warehouseDAO.update(warehouse);
    }

    private Warehouse getWarehouseToUpdate(Warehouse warehouse) {

        return warehouseDAO.findBySid(warehouse.getSid())
                .orElseThrow(() -> WarehouseNotFoundException.forSid(warehouse.getSid()));
    }
}
