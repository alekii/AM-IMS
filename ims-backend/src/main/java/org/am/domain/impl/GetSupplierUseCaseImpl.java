package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetSupplierUseCase;
import org.am.domain.catalog.Supplier;
import org.am.infrastructure.persistence.api.SupplierDAO;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetSupplierUseCaseImpl implements GetSupplierUseCase {

    private final SupplierDAO supplierDAO;

    @Override
    public List<Supplier> getSuppliers() {

        return supplierDAO.findAll();
    }

    @Override
    public Supplier getSupplierBySid(final UUID supplierSid) {

        return supplierDAO.findBySid(supplierSid);
    }
}
