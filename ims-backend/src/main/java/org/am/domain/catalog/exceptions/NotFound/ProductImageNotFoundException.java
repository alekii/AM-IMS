package org.am.domain.catalog.exceptions.NotFound;

import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.NotFoundException;

import java.util.UUID;

public class ProductImageNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 6754323456789098765L;

    public ProductImageNotFoundException(final String message) {

        super(ErrorCode.PRODUCT_IMAGE_NOT_FOUND, message);
    }

    public static ProductImageNotFoundException forSid(final UUID sid) {

        return new ProductImageNotFoundException(String.format("No Image was found for sid = %s", sid));
    }
}
