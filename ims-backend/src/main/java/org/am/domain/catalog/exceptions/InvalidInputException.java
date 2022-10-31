package org.am.domain.catalog.exceptions;

import java.util.List;

public abstract class InvalidInputException extends ImsRestException {

    private static final long serialVersionUID = 6783758678523784348L;

    public InvalidInputException(ErrorCode errorCode, String message) {

        super(errorCode, message);
    }

    public InvalidInputException(final ErrorCode code, final String message, final List<Error> errors) {

        super(code, message, errors);
    }
}
