package org.am.domain.validators.common;

import org.am.domain.catalog.exceptions.Error;
import org.am.domain.catalog.exceptions.validations.InvalidEmailException;
import org.am.domain.validators.constants.ValidationConstants;
import org.am.domain.validators.constants.ValidationErrorConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(ValidationConstants.EMAIL_ADDRESS_REGEX);

    public void validateEmail(String email) {

        Optional<ValidationErrorConstants> errors = validate(email);
        errors.ifPresent(error -> {
            throw buildInvalidEmailException(email, error.name());
        });
    }

    private InvalidEmailException buildInvalidEmailException(String email, String errorName) {

        final List<Error> errors = createError(email, errorName);
        return new InvalidEmailException(String.format("invalid email - %s", email), errors);
    }

    private List<Error> createError(String email, String errorName) {

        List<Error> errorList = new ArrayList<>();
        final Error error = new Error();
        error.setField(email);
        error.setMessage(errorName);
        errorList.add(error);

        return errorList;
    }

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
