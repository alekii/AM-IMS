package org.am.infrastructure.suppliers;

import org.am.library.entities.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SuppplierRepository extends JpaRepository<SupplierEntity, Integer> {

    Optional<SupplierEntity> findById(final Integer id);

    Optional<SupplierEntity> findBySid(final @Param("sid") UUID sid);
}
