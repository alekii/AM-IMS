package org.am.infrastructure.purchases;

import org.am.library.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {

    @Query("SELECT p "
            + "FROM PurchaseEntity p "
            + "INNER JOIN FETCH p.supplier pSupplier "
            + "INNER JOIN FETCH p.lineItems pLineItems"
            + "INNER JOIN FETCH p.warehouse w "
            + "INNER JOIN FETCH w.address wAddress "
            + "INNER JOIN FETCH wAddress.town wTown "
            + "INNER JOIN FETCH wTown.county wCounty "
            + "WHERE p.sid = (:sid)")
    Optional<PurchaseEntity> findBySid(final @Param("sid") UUID sid);

    @Query("SELECT p "
            + "FROM PurchaseEntity p "
            + "INNER JOIN FETCH p.supplier pSupplier "
            + "INNER JOIN FETCH p.lineItems pLineItems"
            + "INNER JOIN FETCH p.warehouse w "
            + "INNER JOIN FETCH w.address wAddress "
            + "INNER JOIN FETCH wAddress.town wTown "
            + "INNER JOIN FETCH wTown.county wCounty ")
    List<PurchaseEntity> fetchAll();
}
