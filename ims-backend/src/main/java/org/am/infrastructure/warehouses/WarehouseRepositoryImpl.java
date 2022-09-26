package org.am.infrastructure.warehouses;

import com.querydsl.core.types.Projections;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.entities.QAddressEntity;
import org.am.library.entities.QCountyEntity;
import org.am.library.entities.QTownEntity;
import org.am.library.entities.WarehouseEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static org.am.library.entities.QWarehouseEntity.warehouseEntity;

@Repository
public class WarehouseRepositoryImpl extends QuerydslRepositorySupport implements WarehouseQueryDslRepository {

    public WarehouseRepositoryImpl() {

        super(WarehouseEntity.class);
    }

    @Override
    public WarehouseProjection findByIdFetch(int id) {

        final QAddressEntity qAddress = QAddressEntity.addressEntity;
        final QTownEntity qTown = QTownEntity.townEntity;
        final QCountyEntity qCounty = QCountyEntity.countyEntity;

        WarehouseProjection warehouseProjection = from(warehouseEntity)
                .innerJoin(warehouseEntity.address, qAddress)
                .innerJoin(qAddress.town, qTown)
                .innerJoin(qTown.county, qCounty)
                .where(
                        warehouseEntity.id.eq(id)
                )
                .select(
                        Projections.constructor(
                                WarehouseProjection.class,
                                warehouseEntity.sid,
                                warehouseEntity.name,
                                warehouseEntity.contactName,
                                warehouseEntity.phoneNumber,
                                warehouseEntity.trackingNumbersCount,
                                warehouseEntity.createdAt,
                                qAddress.street,
                                qAddress.mapUrl,
                                qAddress.longitude,
                                qAddress.latitude,
                                qTown.sid,
                                qTown.name,
                                qCounty.sid,
                                qCounty.name
                        )
                )
                .fetchOne();

        return warehouseProjection;
    }
}
