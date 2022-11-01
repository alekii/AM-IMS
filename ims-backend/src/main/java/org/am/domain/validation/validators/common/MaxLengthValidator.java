package org.am.domain.validation.validators.common;

public class MaxLengthValidator {

    public boolean validateMaxLength(String value, int maxLength) {

        return value.length() <= maxLength;
    }
}
