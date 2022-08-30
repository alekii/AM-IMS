package org.am.domain.catalog.exceptions.validations;

public class InvalidPhoneNumberException extends RuntimeException {

    private static final long serialVersionUID = 8186351614569998689L;

    private InvalidPhoneNumberException(String message) {

        super(message);
    }

    public static InvalidPhoneNumberException forNumber(String phoneNumber) {

        return new InvalidPhoneNumberException(String.format("Phone number is invalid [%s]", phoneNumber));
    }
}
