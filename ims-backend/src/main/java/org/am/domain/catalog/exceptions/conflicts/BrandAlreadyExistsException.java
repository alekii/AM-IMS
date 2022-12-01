package org.am.domain.catalog.exceptions.conflicts;

import org.am.domain.catalog.exceptions.ConflictException;
import org.am.domain.catalog.exceptions.ErrorCode;

public class BrandAlreadyExistsException extends ConflictException {

    private static final long serialVersionUID = 2839752064732479046L;

    public BrandAlreadyExistsException(String message) {

        super(ErrorCode.BRAND_ALREADY_EXISTS, message);
    }

    public static BrandAlreadyExistsException forName(String brandName) {

        return new BrandAlreadyExistsException(String.format("Brand Already Exists With Name = %s", brandName));
    }
}
