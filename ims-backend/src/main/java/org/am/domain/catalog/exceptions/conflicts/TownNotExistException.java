package org.am.domain.catalog.exceptions.conflicts;

import org.am.domain.catalog.exceptions.ConflictException;
import org.am.domain.catalog.exceptions.ErrorCode;

import java.util.UUID;

public class TownNotExistException extends ConflictException {

    private static final long serialVersionUID = 7430846128432598674L;

    public TownNotExistException(String message) {

        super(ErrorCode.TOWN_NOT_EXIST, message);
    }

    public static TownNotExistException forSid(UUID sid) {

        return new TownNotExistException(String.format("Town not found with sid = %s", sid));
    }
}
