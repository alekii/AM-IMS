package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.library.entities.PurchaseEntity;

import java.util.List;
import java.util.UUID;

public interface PurchaseDAO {

    Purchase create(PurchaseEntity purchase, final List<Product> products);

    PurchaseEntity findBySid(final UUID sid);

    List<Purchase> findAll();

    Purchase update(final PurchaseEntity purchase, List<Product> products);
}
