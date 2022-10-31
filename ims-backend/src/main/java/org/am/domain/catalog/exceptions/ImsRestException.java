package org.am.domain.catalog.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ImsRestException extends RuntimeException {

    private static final long serialVersionUID = 5488044252354196215L;

    private final ErrorCode errorCode;

    private final String message;

    private List<Error> errors;

    public ImsRestException(final ErrorCode code, final String message) {

        this.errorCode = code;
        this.message = message;
    }

    public ImsRestException(final ErrorCode code, final String message, final List<Error> errors) {

        this.errorCode = code;
        this.message = message;
        this.errors = errors;
    }
}
