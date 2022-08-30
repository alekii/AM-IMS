package org.am.infrastructure.Towns;

import org.am.library.entities.TownEntity;

import java.util.UUID;

public interface TownQueryDslRepository {

    TownEntity findBySID(final UUID townSid);
}
