package org.am.infrastructure.lineItems;

import org.am.library.entities.PurchaseProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LineItemsRepository extends JpaRepository<PurchaseProductEntity, Integer> {

    @Query(
            "SELECT p "
                    + "FROM PurchaseProductEntity p "
                    + "INNER JOIN FETCH p.product product "
                    + "WHERE product.id = (:id)"
    )
    PurchaseProductEntity findByProductId(final @Param("id") int productId);

    @Query(
            "SELECT p "
                    + "FROM PurchaseProductEntity p "
                    + "INNER JOIN FETCH p.purchase purchase "
                    + "WHERE purchase.id = (:id)"
    )
    List<PurchaseProductEntity> findByPurchaseId(final @Param("id") int purchaseId);

    @Modifying
    @Query(
            "DELETE "
                    + "FROM PurchaseProductEntity p "
                    + "WHERE p.product.id = (:id)"
    )
    void deleteByProductId(final @Param("id") int productId);
}
