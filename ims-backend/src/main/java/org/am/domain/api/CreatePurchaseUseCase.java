package org.am.domain.api;

import org.am.domain.catalog.Purchase;

public interface CreatePurchaseUseCase {

    Purchase create(Purchase purchase);
}
