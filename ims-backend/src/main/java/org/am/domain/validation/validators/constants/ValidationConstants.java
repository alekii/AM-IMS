package org.am.domain.validation.validators.constants;

public class ValidationConstants {

    public static final String PHONE_NUMBER_REGEX = "(\\+254|0)?7[0-9]{8}";

    public static final String EMAIL_ADDRESS_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static final int EMAIL_MAX_LENGTH = 255;

    public static final int NAME_MAX_LENGTH = 255;

    public static final String INVALID_EMAIL = "email must be valid";

    public static final String INVALID_PHONE_NUMBER = "phone must be kenyan";
}
