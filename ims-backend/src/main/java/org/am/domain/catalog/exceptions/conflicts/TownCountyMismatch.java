package org.am.domain.catalog.exceptions.conflicts;

import org.am.domain.catalog.exceptions.ConflictException;
import org.am.domain.catalog.exceptions.ErrorCode;

import java.util.UUID;

public class TownCountyMismatch extends ConflictException {

    private static final long serialVersionUID = 4532178653456789234L;

    public TownCountyMismatch(String message) {

        super(ErrorCode.INVALID_RESOURCE, message);
    }

    public static TownCountyMismatch forSid(UUID sid) {

        return new TownCountyMismatch(String.format("Town with sid = %s is not present in provided county", sid));
    }
}
