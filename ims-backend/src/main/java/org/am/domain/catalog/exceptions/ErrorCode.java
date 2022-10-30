package org.am.domain.catalog.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_RESOURCE(10001),
    // warehouse errors
    WAREHOUSE_NOT_FOUND(200001),
    SUPPLIER_NOT_FOUND(200001),
    TOWN_NOT_EXIST(200002),
    WAREHOUSE_ALREADY_EXISTS(200003),
    INVALID_PHONE_NUMBER(200004);

    private final int id;

    public int getValue() {

        return id;
    }
}
