package org.am.rest.services.requests;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class SupplierUpdateRequest {

    String name;

    String email;

    String phoneNumber;

    int leadTime;
}
