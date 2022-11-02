package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.UpdateSupplierUseCase;
import org.am.domain.catalog.Supplier;
import org.am.domain.validation.validators.SupplierValidator;
import org.am.infrastructure.persistence.api.SupplierDAO;

@RequiredArgsConstructor
public class UpdateSupplierUseCaseImpl implements UpdateSupplierUseCase {

    private final SupplierDAO supplierDAO;

    private final SupplierValidator supplierValidator;

    @Override
    public Supplier update(Supplier supplier) {

        supplierValidator.validate(supplier);
        return supplierDAO.updateSupplier(supplier);
    }
}
