package org.am.domain.catalog.exceptions.NotFound;

import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.NotFoundException;

import java.util.UUID;

public class CategoryNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 1863456327738453268L;

    public CategoryNotFoundException(String message) {

        super(ErrorCode.CATEGORY_NOT_FOUND, message);
    }

    public static CategoryNotFoundException forSid(UUID sid) {

        return new CategoryNotFoundException(String.format("No Category found for sid = %s", sid));
    }
}
