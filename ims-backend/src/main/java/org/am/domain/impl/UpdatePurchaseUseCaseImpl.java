package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.UpdatePurchaseUseCase;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.library.entities.PurchaseEntity;

import java.util.List;

@RequiredArgsConstructor
public class UpdatePurchaseUseCaseImpl implements UpdatePurchaseUseCase {

    private final PurchaseDAO purchaseDAO;

    @Override
    public Purchase update(Purchase purchase, List<Product> products) {

        final PurchaseEntity purchaseEntity = purchaseDAO.findBySid(purchase.getSid());
        return purchaseDAO.update(purchaseEntity, products);
    }
}
