package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.WarehouseCreation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseDAO {

    Warehouse create(WarehouseCreation warehouseCreation);

    List<Warehouse> findAll();

    Optional<Warehouse> findBySid(UUID warehouseSid);
}
