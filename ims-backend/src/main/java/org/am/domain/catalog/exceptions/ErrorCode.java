package org.am.domain.catalog.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_RESOURCE(10001),
    // warehouse errors
    WAREHOUSE_NOT_FOUND(200001),
    SUPPLIER_NOT_FOUND(200002),
    TOWN_NOT_EXIST(200003),
    WAREHOUSE_ALREADY_EXISTS(200004),
    INVALID_PHONE_NUMBER(200005),
    INVALID_EMAIL_ADDRESS(200006);

    private final int id;

    public int getValue() {

        return id;
    }
}
