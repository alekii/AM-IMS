package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.catalog.Purchase;
import org.am.infrastructure.persistence.api.PurchaseDAO;

@RequiredArgsConstructor
public class CreatePurchaseUseCaseImpl implements CreatePurchaseUseCase {

    private final PurchaseDAO purchaseDAO;

    @Override
    public Purchase create(Purchase purchase) {

        return purchaseDAO.create(purchase);
    }
}
