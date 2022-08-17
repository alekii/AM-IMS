package org.am.infrastructure.warehouses.projections;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class WarehouseProjection {

    final private UUID sid;

    final private String name;

    final private String contactName;

    final private String phoneNumber;

    final private Integer trackingNumberCount;

    final private String addressStreet;

    final private String addressMapUrl;

    final private Double addressLongitude;

    final private Double addressLatitude;

    final private UUID townSid;

    final private String townName;

    final private UUID countySid;

    final private String countyName;
}
