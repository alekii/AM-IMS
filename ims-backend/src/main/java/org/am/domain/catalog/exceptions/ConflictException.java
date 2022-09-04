package org.am.domain.catalog.exceptions;

public class ConflictException extends ImsRestException {

    private static final long serialVersionUID = 2346278374834637823L;

    public ConflictException(ErrorCode errorCode, String message) {

        super(errorCode, message);
    }
}
