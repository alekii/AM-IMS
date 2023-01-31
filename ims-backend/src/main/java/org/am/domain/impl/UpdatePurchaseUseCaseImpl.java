package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.UpdatePurchaseUseCase;
import org.am.domain.catalog.Purchase;
import org.am.infrastructure.persistence.api.PurchaseDAO;

@RequiredArgsConstructor
public class UpdatePurchaseUseCaseImpl implements UpdatePurchaseUseCase {

    private final PurchaseDAO purchaseDAO;

    @Override
    public Purchase update(Purchase purchase) {

        return purchaseDAO.update(purchase);
    }
}
