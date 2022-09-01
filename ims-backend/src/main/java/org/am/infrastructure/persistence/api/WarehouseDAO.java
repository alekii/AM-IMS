package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Warehouse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseDAO {

    Warehouse create(Warehouse warehouse);

    List<Warehouse> findAll();

    Optional<Warehouse> findBySid(UUID warehouseSid);
}
