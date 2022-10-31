package org.am.domain.validation.validators;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Supplier;
import org.am.domain.validation.ValidationUtil;
import org.am.domain.validation.validators.common.EmailValidator;
import org.am.domain.validation.validators.constants.ValidationErrorConstants;

import java.util.Optional;

@RequiredArgsConstructor
public class SupplierValidator {

    private final EmailValidator emailValidator;

    public void validate(Supplier supplier) {

        validateEmail(supplier.getEmail());
    }

    public void validateEmail(String email) {

        Optional<ValidationErrorConstants> errors = emailValidator.validate(email);
        errors.ifPresent(error -> {
            throw ValidationUtil.buildInvalidEmailException(email, error.name());
        });
    }
}
