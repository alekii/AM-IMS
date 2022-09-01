package org.am.domain.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class WarehouseCreation {

    String name;

    String phoneNumber;

    String contactName;

    Address address;

    UUID sid;

    Integer trackingNumbersCount;
}
