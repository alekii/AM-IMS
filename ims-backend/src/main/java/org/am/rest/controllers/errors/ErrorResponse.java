package org.am.rest.controllers.errors;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder(builderClassName = "Builder")
public
class ErrorResponse {

    int code;

    String message;

    Collection<Error> errors;
}
