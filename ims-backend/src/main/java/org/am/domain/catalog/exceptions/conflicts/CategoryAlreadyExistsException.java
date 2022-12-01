package org.am.domain.catalog.exceptions.conflicts;

import org.am.domain.catalog.exceptions.ConflictException;
import org.am.domain.catalog.exceptions.ErrorCode;

public class CategoryAlreadyExistsException extends ConflictException {

    private static final long serialVersionUID = 3672354905132945208L;

    public CategoryAlreadyExistsException(String message) {

        super(ErrorCode.CATEGORY_ALREADY_EXISTS, message);
    }

    public static CategoryAlreadyExistsException forName(String categoryName) {

        return new CategoryAlreadyExistsException(String.format("Category Already Exists With Name = %s", categoryName));
    }
}
