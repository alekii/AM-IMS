package org.am.infrastructure.warehouses;

import org.am.library.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer>, WarehouseQueryDslRepository {
    Warehouse findBySid(final UUID sid);

    Optional<Warehouse> findById(final Integer id);
}
