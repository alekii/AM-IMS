package org.am.infrastructure.warehouses.projections;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Builder(builderClassName = "Builder")
public class WarehouseProjection {

    final private UUID sid;

    final private String name;

    final private String contactName;

    final private String phoneNumber;

    final private Integer trackingNumberCount;

    final private Instant createdAt;

    final private String addressStreet;

    final private String addressMapUrl;

    final private Double addressLongitude;

    final private Double addressLatitude;

    final private UUID townSid;

    final private String townName;

    final private UUID countySid;

    final private String countyName;
}
