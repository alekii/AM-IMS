package org.am.domain.catalog.exceptions.validations;

import org.am.domain.catalog.exceptions.Error;
import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.InvalidInputException;

import java.util.List;

public class InvalidEmailException extends InvalidInputException {

    public InvalidEmailException(String message, List<Error> errors) {

        super(ErrorCode.INVALID_EMAIL_ADDRESS, message, errors);
    }
}
