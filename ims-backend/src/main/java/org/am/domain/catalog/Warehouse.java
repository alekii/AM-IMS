package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class Warehouse {

    UUID sid;

    String name;

    String phoneNumber;

    String contactName;

    Address address;
}
