package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class Supplier {

    UUID sid;

    int leadTime;

    String name;

    String email;

    String phoneNumber;
}
