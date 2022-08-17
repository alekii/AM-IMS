package org.am.infrastructure.warehouses;

import com.querydsl.core.types.Projections;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.entities.QAddress;
import org.am.library.entities.QCounty;
import org.am.library.entities.QTown;
import org.am.library.entities.Warehouse;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static org.am.library.entities.QTown.town;
import static org.am.library.entities.QWarehouse.warehouse;

public class WarehouseRepositoryImpl extends QuerydslRepositorySupport implements WarehouseQueryDslRepository{

    public WarehouseRepositoryImpl() {

        super(Warehouse.class);
    }
    @Override
    public WarehouseProjection findByIdFetch(int id) {
        final QAddress qAddress = QAddress.address;
        final QTown qTown = town;
        final QCounty qCounty = QCounty.county;

        WarehouseProjection warehouseProjection = from(warehouse)
                .innerJoin(warehouse.warehouseAddress, qAddress)
                .innerJoin(qAddress.town, qTown)
                .innerJoin(qTown.county, qCounty)
                .where(
                        warehouse.id.eq(id)
                )
                .select(
                        Projections.constructor(
                                WarehouseProjection.class,
                                warehouse.sid,
                                warehouse.name,
                                warehouse.contactName,
                                warehouse.phoneNumber,
                                warehouse.trackingNumbersCount,
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
