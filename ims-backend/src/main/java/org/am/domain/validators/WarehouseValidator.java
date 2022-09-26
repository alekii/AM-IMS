package org.am.domain.validators;

import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.validations.InvalidPhoneNumberException;
import org.am.domain.validators.constants.ValidationConstants;

import java.util.regex.Pattern;

public class WarehouseValidator {

    public static final Pattern PHONE_PATTERN = Pattern.compile(ValidationConstants.PHONE_NUMBER_REGEX);

    public void validatePhoneNumber(Warehouse warehouse) {

        if (warehouse.getPhoneNumber() == null) {
            return;
        }

        final String phoneNumber = warehouse.getPhoneNumber();
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw InvalidPhoneNumberException.forNumber(phoneNumber);
        }
    }
}
