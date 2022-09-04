package org.am.domain.catalog.exceptions.validations;

import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.InvalidInputException;

public class InvalidPhoneNumberException extends InvalidInputException {

    private static final long serialVersionUID = 3854733432677987954L;

    private InvalidPhoneNumberException(String message) {

        super(ErrorCode.INVALID_PHONE_NUMBER, message);
    }

    public static InvalidPhoneNumberException forNumber(String phoneNumber) {

        return new InvalidPhoneNumberException(String.format("Phone number is invalid [%s]", phoneNumber));
    }
}
