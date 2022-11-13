package org.am.domain.catalog.exceptions.NotFound;

import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.NotFoundException;

import java.util.UUID;

public class ProductNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 6754323456789098765L;

    public ProductNotFoundException(final String message) {

        super(ErrorCode.PRODUCT_NOT_FOUND, message);
    }

    public static SupplierNotFoundException forSid(final UUID sid) {

        return new SupplierNotFoundException(String.format("No Product was found for sid = %s", sid));
    }
}
