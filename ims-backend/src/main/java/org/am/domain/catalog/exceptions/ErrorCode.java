package org.am.domain.catalog.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    WAREHOUSE_NOT_FOUND(1),
    TOWN_NOT_EXIST(2),
    WAREHOUSE_ALREADY_EXISTS(3),
    INVALID_PHONE_NUMBER(4),
    VALIDATION_ERROR(5);

    private final int id;

    public int getValue() {

        return id;
    }
}
