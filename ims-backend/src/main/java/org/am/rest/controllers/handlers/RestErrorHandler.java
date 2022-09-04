package org.am.rest.controllers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.am.domain.catalog.exceptions.ConflictException;
import org.am.domain.catalog.exceptions.ImsRestException;
import org.am.domain.catalog.exceptions.InvalidInputException;
import org.am.domain.catalog.exceptions.NotFoundException;
import org.am.rest.controllers.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class RestErrorHandler {

    private static final String REST_ERROR = "Rest Error";

    //    @Hidden
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {

        log.warn(REST_ERROR, exception);
        return handleImsException(exception, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException exception) {

        log.warn(REST_ERROR, exception);

        return handleImsException(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<ErrorResponse> handleConflictException(ConflictException exception) {

        log.warn(REST_ERROR, exception);
        return handleImsException(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleImsException(ImsRestException e, HttpStatus status) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getErrorCode().getValue())
                .message(e.getMessage())
                .errors(e.getErrors())
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}
