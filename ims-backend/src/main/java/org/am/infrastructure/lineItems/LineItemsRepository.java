package org.am.infrastructure.lineItems;

import org.am.library.entities.PurchaseProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemsRepository extends JpaRepository<PurchaseProductEntity, Integer> {

}
