package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class Warehouse {

    UUID sid;

    String name;

    String phoneNumber;

    String contactName;

    Instant createdAt;

    Address address;
}
