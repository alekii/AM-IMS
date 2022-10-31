package org.am.domain.validators;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Supplier;
import org.am.domain.validators.common.EmailValidator;

@RequiredArgsConstructor
public class SupplierValidator {

    private final EmailValidator emailValidator;

    public void validate(Supplier supplier) {

        emailValidator.validateEmail(supplier.getEmail());
    }
}
