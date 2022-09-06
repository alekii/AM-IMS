package org.am.rest.controllers.handlers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.am.domain.catalog.exceptions.ConflictException;
import org.am.domain.catalog.exceptions.ImsRestException;
import org.am.domain.catalog.exceptions.InvalidInputException;
import org.am.domain.catalog.exceptions.NotFoundException;
import org.am.rest.controllers.errors.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
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
    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity<String> handleInvalidFormatException(InvalidFormatException exception) {

        log.warn(REST_ERROR, exception);

        return new ResponseEntity<>(String.format("%s is invalid UUID", exception.getValue()), HttpStatus.BAD_REQUEST);
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

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {

        log.warn(REST_ERROR, exception);

        Collection<Error> errors = exception.getConstraintViolations()
                .stream()
                .map(violation -> new Error(violation.getMessage()))
                .collect(Collectors.toSet());

        Map<String, Collection<Error>> result = new HashMap<>();
        result.put("errors", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        log.warn(REST_ERROR, ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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
