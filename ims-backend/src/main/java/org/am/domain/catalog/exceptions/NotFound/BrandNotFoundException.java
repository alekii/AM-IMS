package org.am.domain.catalog.exceptions.NotFound;

import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.NotFoundException;

import java.util.UUID;

public class BrandNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 8456372836453490546L;

    public BrandNotFoundException(String message) {

        super(ErrorCode.BRAND_NOT_FOUND, message);
    }

    public static BrandNotFoundException forSid(UUID sid) {

        return new BrandNotFoundException(String.format("No Brand found for sid = %s", sid));
    }
}
