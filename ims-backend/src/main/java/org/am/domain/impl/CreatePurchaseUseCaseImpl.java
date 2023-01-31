package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.api.SupplierDAO;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.am.library.entities.PurchaseEntity;

import java.util.UUID;

@RequiredArgsConstructor
public class CreatePurchaseUseCaseImpl implements CreatePurchaseUseCase {

    private final PurchaseDAO purchaseDAO;

    private final SupplierDAO supplierDAO;

    private final PurchaseToPurchaseEntityConverter purchaseEntityConverter;

    @Override
    public Purchase create(Purchase purchase) {

        final Supplier supplier = validateAndGetSupplier(purchase.getSupplier().getSid());

        purchase.setSupplier(supplier);

        final PurchaseEntity purchaseEntity = purchaseEntityConverter.convert(purchase);

        return purchaseDAO.create(purchaseEntity, purchase.getProducts());
    }

    private Supplier validateAndGetSupplier(UUID supplierSid) {

        return supplierDAO.findBySid(supplierSid);
    }
}
