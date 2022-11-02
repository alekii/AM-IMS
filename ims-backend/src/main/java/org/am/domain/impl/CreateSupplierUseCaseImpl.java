package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateSupplierUseCase;
import org.am.domain.catalog.Supplier;
import org.am.domain.validation.validators.SupplierValidator;
import org.am.infrastructure.persistence.api.SupplierDAO;

@RequiredArgsConstructor
public class CreateSupplierUseCaseImpl implements CreateSupplierUseCase {

    private final SupplierDAO supplierDAO;

    private final SupplierValidator supplierValidator;

    @Override
    public Supplier create(final Supplier supplier) {

        supplierValidator.validate(supplier);

        return supplierDAO.create(supplier);
    }
}
