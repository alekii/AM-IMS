package org.am.domain.catalog.exceptions.conflicts;

import org.am.domain.catalog.exceptions.ConflictException;
import org.am.domain.catalog.exceptions.ErrorCode;

public class WarehouseAlreadyExistsException extends ConflictException {

    private static final long serialVersionUID = 4578345623906754361L;

    public WarehouseAlreadyExistsException(String message) {

        super(ErrorCode.WAREHOUSE_ALREADY_EXISTS, message);
    }

    public static WarehouseAlreadyExistsException forName(String warehouseName) {

        return new WarehouseAlreadyExistsException(String.format("Warehouse Already Exists With Name = %s", warehouseName));
    }
}

