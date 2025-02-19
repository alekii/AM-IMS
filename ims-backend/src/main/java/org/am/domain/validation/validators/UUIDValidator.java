package org.am.domain.validation.validators;

import org.am.domain.validation.annotations.UUIDPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<UUIDPattern, UUID> {

    private final String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    @Override
    public void initialize(UUIDPattern validUuid) {

    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext cxt) {

        if (uuid != null) {
            return uuid.toString().matches(this.regex);
        }
        return false;
    }
}
