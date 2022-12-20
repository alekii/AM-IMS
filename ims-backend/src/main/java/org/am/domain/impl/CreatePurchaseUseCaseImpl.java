package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;

import java.util.List;

@RequiredArgsConstructor
public class CreatePurchaseUseCaseImpl implements CreatePurchaseUseCase {

    private final PurchaseDAO purchaseDAO;

    private final PurchaseToPurchaseEntityConverter purchaseEntityConverter;

    @Override
    public Purchase create(Purchase purchase, List<Product> products) {

        return purchaseDAO.create(purchaseEntityConverter.convert(purchase), products);
    }
}
