package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetPurchaseUseCase;
import org.am.domain.catalog.Purchase;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.converters.PurchaseEntityToPurchaseConverter;
import org.am.library.entities.PurchaseEntity;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetPurchaseUseCaseImpl implements GetPurchaseUseCase {

    private final PurchaseDAO purchaseDAO;

    private final PurchaseEntityToPurchaseConverter purchaseConverter;

    @Override
    public Purchase findBySid(final UUID purchaseSid) {

        PurchaseEntity purchaseEntity = purchaseDAO.findBySid(purchaseSid);
        return purchaseConverter.convert(purchaseEntity);
    }

    @Override
    public List<Purchase> getAllPurchases() {

        return purchaseDAO.findAll();
    }
}
