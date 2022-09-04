package org.am.domain.catalog.exceptions;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
public class ImsRestException extends RuntimeException {

    private static final long serialVersionUID = 5488044252354196215L;

    private final ErrorCode errorCode;

    private final String message;

    private final Collection<Error> errors = Collections.emptyList();

    public ImsRestException(final ErrorCode code, final String message) {

        this.errorCode = code;
        this.message = message;
    }
}
