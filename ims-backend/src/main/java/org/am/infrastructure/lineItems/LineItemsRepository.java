package org.am.infrastructure.lineItems;

import org.am.library.entities.PurchaseProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LineItemsRepository extends JpaRepository<PurchaseProductEntity, Integer> {

    @Query(
            "SELECT p "
                    + "FROM PurchaseProductEntity p "
                    + "INNER JOIN FETCH p.purchase purchase "
                    + "WHERE purchase.id = (:purchaseId)"
    )
    List<PurchaseProductEntity> findByPurchaseId(final @Param("id") int purchaseId);
}
