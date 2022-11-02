package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateWarehouseUseCase;
import org.am.domain.catalog.Warehouse;
import org.am.domain.validation.validators.WarehouseValidator;
import org.am.infrastructure.persistence.api.WarehouseDAO;

@RequiredArgsConstructor
public class CreateWarehouseUseCaseImpl implements CreateWarehouseUseCase {

    private final WarehouseDAO warehouseDAO;

    private final WarehouseValidator warehouseValidator;

    @Override
    public Warehouse create(Warehouse warehouse) {

        warehouseValidator.validatePhoneNumber(warehouse);

        return warehouseDAO.create(warehouse);
    }
}
