package org.am.rest.services.responses;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SupplierResponse {

    UUID sid;

    String name;

    String email;

    String phoneNumber;

    int leadTime;
}
