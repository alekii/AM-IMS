package org.am.rest.services.responses;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@RequiredArgsConstructor
public class SupplierResponse {

    UUID sid;

    String name;

    String email;

    String phoneNumber;

    int leadTime;
}
