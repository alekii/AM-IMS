package org.am.domain.validation;

import org.am.domain.catalog.exceptions.Error;
import org.am.domain.catalog.exceptions.validations.InvalidEmailException;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {

    public static InvalidEmailException buildInvalidEmailException(String email, String errorName) {

        final List<Error> errors = createError(email, errorName);
        return new InvalidEmailException(String.format("Email is invalid - %s", email), errors);
    }

    private static List<Error> createError(String email, String errorName) {

        List<Error> errorList = new ArrayList<>();
        final Error error = new Error();
        error.setField(email);
        error.setMessage(errorName);
        errorList.add(error);

        return errorList;
    }
}
