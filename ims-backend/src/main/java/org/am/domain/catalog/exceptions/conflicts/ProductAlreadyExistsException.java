package org.am.domain.catalog.exceptions.conflicts;

import org.am.domain.catalog.exceptions.ConflictException;
import org.am.domain.catalog.exceptions.ErrorCode;

public class ProductAlreadyExistsException extends ConflictException {

    private static final long serialVersionUID = 1029384756378219208L;

    public ProductAlreadyExistsException(String message) {

        super(ErrorCode.BRAND_ALREADY_EXISTS, message);
    }

    public static ProductAlreadyExistsException forName(String productName) {

        return new ProductAlreadyExistsException(String.format("Product Already Exists With Name = %s", productName));
    }
}
