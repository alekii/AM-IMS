package org.am.domain.catalog.exceptions.NotFound;

import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.NotFoundException;

import java.util.UUID;

public class WarehouseNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 4123985678321056732L;

    public WarehouseNotFoundException(String message) {

        super(ErrorCode.WAREHOUSE_NOT_FOUND, message);
    }

    public static WarehouseNotFoundException forSid(UUID sid) {

        return new WarehouseNotFoundException(String.format("No Warehouse found for sid = %s", sid));
    }
}
