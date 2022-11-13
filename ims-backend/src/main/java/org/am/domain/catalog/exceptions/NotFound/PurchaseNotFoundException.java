package org.am.domain.catalog.exceptions.NotFound;

import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.NotFoundException;

import java.util.UUID;

public class PurchaseNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 3432124567890654367L;

    public PurchaseNotFoundException(final String message) {

        super(ErrorCode.PURCHASE_NOT_FOUND, message);
    }

    public static PurchaseNotFoundException forSid(final UUID sid) {

        return new PurchaseNotFoundException(String.format("No Purchase was found for sid = %s", sid));
    }
}
