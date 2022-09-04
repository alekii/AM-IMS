package org.am.domain.catalog.exceptions;

public abstract class NotFoundException extends ImsRestException {

    private static final long serialVersionUID = 4894639134786247256L;

    public NotFoundException(ErrorCode errorCode, String message) {

        super(errorCode, message);
    }
}

