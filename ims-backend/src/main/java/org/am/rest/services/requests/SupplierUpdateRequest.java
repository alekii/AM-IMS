package org.am.rest.services.requests;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class SupplierUpdateRequest {

    UUID sid;

    String name;

    String email;

    String phoneNumber;

    int leadTime;
}
