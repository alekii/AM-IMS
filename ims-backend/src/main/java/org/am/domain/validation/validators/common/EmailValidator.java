package org.am.domain.validation.validators.common;

import org.am.domain.validation.validators.constants.ValidationConstants;
import org.am.domain.validation.validators.constants.ValidationErrorConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(ValidationConstants.EMAIL_ADDRESS_REGEX);

    public Optional<ValidationErrorConstants> validate(String email) {

        if (StringUtils.isBlank(email)) {
            return Optional.of(ValidationErrorConstants.BLANK_EMAIL);
        }

        if (!isValidLength(email)) {
            return Optional.of(ValidationErrorConstants.EMAIL_MORE_THAN_255_CHARACTERS);
        }

        if (!isValid(email)) {
            return Optional.of(ValidationErrorConstants.INVALID_EMAIL);
        }

        return Optional.empty();
    }

    private boolean isValidLength(String email) {

        return email.length() <= ValidationConstants.EMAIL_MAX_LENGTH;
    }

    private boolean isValid(String email) {

        return EMAIL_PATTERN.matcher(email).matches();
    }
}
