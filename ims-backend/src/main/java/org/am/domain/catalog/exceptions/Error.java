package org.am.domain.catalog.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Error implements Serializable {

    private static final long serialVersionUID = 3412345678901234567L;

    private String field;

    private String message;
}
