package org.am.domain.catalog.exceptions.NotFound;

import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.NotFoundException;

import java.util.UUID;

public class SupplierNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 5432789634215678543L;

    public SupplierNotFoundException(String message) {

        super(ErrorCode.SUPPLIER_NOT_FOUND, message);
    }

    public static SupplierNotFoundException forSid(UUID sid) {

        return new SupplierNotFoundException(String.format("No Supplier found for sid = %s", sid));
    }
}
