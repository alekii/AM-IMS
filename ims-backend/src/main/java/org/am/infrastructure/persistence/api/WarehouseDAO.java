package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Warehouse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseDAO {

    List<Warehouse> findAll();

    Optional<Warehouse> findBySid(UUID warehouseSid);
}
