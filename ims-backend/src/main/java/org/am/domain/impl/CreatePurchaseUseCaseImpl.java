package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.catalog.Purchase;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.am.library.entities.PurchaseEntity;

@RequiredArgsConstructor
public class CreatePurchaseUseCaseImpl implements CreatePurchaseUseCase {

    private final PurchaseDAO purchaseDAO;

    private final PurchaseToPurchaseEntityConverter purchaseEntityConverter;

    @Override
    public Purchase create(Purchase purchase) {

        final PurchaseEntity purchaseEntity = purchaseEntityConverter.convert(purchase);

        return purchaseDAO.create(purchaseEntity, purchase.getProducts());
    }
}
