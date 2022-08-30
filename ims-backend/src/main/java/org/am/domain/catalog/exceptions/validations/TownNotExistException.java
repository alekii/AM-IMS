package org.am.domain.catalog.exceptions.validations;

import java.util.UUID;

public class TownNotExistException extends RuntimeException {

    public TownNotExistException(String message) {

        super(message);
    }

    public static TownNotExistException forSid(UUID sid) {

        return new TownNotExistException(String.format("Town not found with sid = %s", sid));
    }
}
