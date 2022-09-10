package org.am.domain.catalog.exceptions;

public abstract class InvalidInputException extends ImsRestException {

    private static final long serialVersionUID = 6783758678523784348L;

    public InvalidInputException(ErrorCode errorCode, String message) {

        super(errorCode, message);
    }
}
