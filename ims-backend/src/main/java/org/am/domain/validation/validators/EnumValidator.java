package org.am.domain.validation.validators;

import org.am.domain.validation.annotations.EnumConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class EnumValidator implements ConstraintValidator<EnumConstraint, String> {

    private Class<? extends Enum<?>> enumClazz;

    @Override
    public void initialize(EnumConstraint constraintAnnotation) {

        enumClazz = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return Arrays.stream(enumClazz.getEnumConstants())
                .map(Objects::toString)
                .anyMatch(enumValue -> enumValue.equals(value.toUpperCase()));
    }
}
