package org.am.infrastructure.warehouses;

import org.am.library.entities.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Integer>, WarehouseQueryDslRepository {

    Optional<WarehouseEntity> findById(final Integer id);

    Optional<WarehouseEntity> findBySid(final UUID sid);

    @Query("SELECT w.sid FROM WarehouseEntity w WHERE LOWER(w.name) = LOWER(:name)")
    Optional<UUID> findSidByName(final @Param("name") String warehouseName);
}
