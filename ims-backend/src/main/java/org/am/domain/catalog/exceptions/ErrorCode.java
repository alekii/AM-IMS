package org.am.domain.catalog.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_RESOURCE(10001),
    // warehouse errors
    WAREHOUSE_NOT_FOUND(20001),
    SUPPLIER_NOT_FOUND(20002),
    TOWN_NOT_EXIST(20003),
    WAREHOUSE_ALREADY_EXISTS(20004),
    INVALID_PHONE_NUMBER(20005),
    INVALID_EMAIL_ADDRESS(20006),
    //stock
    PRODUCT_NOT_FOUND(30000),
    PURCHASE_NOT_FOUND(30001),
    CATEGORY_NOT_FOUND(30002),
    BRAND_NOT_FOUND(30003);

    private final int id;

    public int getValue() {

        return id;
    }
}
