package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Warehouse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseDAO {

    Warehouse create(final Warehouse warehouse);

    List<Warehouse> findAll();

    Optional<Warehouse> findBySid(final UUID warehouseSid);

    Warehouse update(final Warehouse warehouse);
}
