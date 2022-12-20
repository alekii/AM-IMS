package org.am.domain.api;

import org.am.domain.catalog.Purchase;

import java.util.List;
import java.util.UUID;

public interface GetPurchaseUseCase {

    Purchase findBySid(final UUID sid);

    List<Purchase> getAllPurchases();
}
