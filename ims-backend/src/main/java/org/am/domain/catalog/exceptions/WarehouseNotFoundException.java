package org.am.domain.catalog.exceptions;

import java.util.UUID;

public class WarehouseNotFoundException extends RuntimeException {

    public WarehouseNotFoundException(String message) {

        super(message);
    }

    public static WarehouseNotFoundException forSid(UUID sid) {

        return new WarehouseNotFoundException(String.format("No Warehouse found for sid = %s", sid));
    }
}
