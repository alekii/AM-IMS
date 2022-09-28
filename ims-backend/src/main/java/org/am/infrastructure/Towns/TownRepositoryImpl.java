package org.am.infrastructure.Towns;

import org.am.library.entities.QCountyEntity;
import org.am.library.entities.QTownEntity;
import org.am.library.entities.TownEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.am.library.entities.QTownEntity.townEntity;

@Repository
public class TownRepositoryImpl extends QuerydslRepositorySupport implements TownQueryDslRepository {

    public TownRepositoryImpl() {

        super(TownEntity.class);
    }

    @Override
    public TownEntity findBySID(UUID townSid) {

        final QTownEntity qTown = QTownEntity.townEntity;
        final QCountyEntity qCounty = QCountyEntity.countyEntity;
        return from(townEntity)
                .where(townEntity.sid.eq(townSid))
                .innerJoin(qTown.county, qCounty)
                .fetchOne();
    }
}
