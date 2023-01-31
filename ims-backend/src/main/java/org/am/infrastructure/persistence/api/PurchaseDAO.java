package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Purchase;
import org.am.library.entities.PurchaseEntity;

import java.util.List;
import java.util.UUID;

public interface PurchaseDAO {

    Purchase create(final Purchase purchase);

    PurchaseEntity findBySid(final UUID sid);

    List<Purchase> findAll();

    Purchase update(final Purchase purchase);
}
