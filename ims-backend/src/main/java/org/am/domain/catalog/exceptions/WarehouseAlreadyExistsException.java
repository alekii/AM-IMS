package org.am.domain.catalog.exceptions;

public class WarehouseAlreadyExistsException extends RuntimeException {

    public WarehouseAlreadyExistsException(String message) {

        super(message);
    }

    public static WarehouseAlreadyExistsException forName(String warehouseName) {

        return new WarehouseAlreadyExistsException(String.format("Warehouse Already Exists With Name = %s", warehouseName));
    }
}

