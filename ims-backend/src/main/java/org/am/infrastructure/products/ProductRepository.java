package org.am.infrastructure.products;

import org.am.library.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query("SELECT p "
            + "FROM ProductEntity p "
            + "INNER JOIN FETCH p.brand pBrand "
            + "INNER JOIN FETCH p.category pCategory "
            + "INNER JOIN FETCH p.supplier pSupplier "
            + "WHERE p.sid = (:sid)")
    Optional<ProductEntity> findBySid(final @Param("sid") UUID sid);
}
