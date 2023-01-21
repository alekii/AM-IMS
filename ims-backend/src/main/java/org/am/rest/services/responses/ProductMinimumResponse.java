package org.am.rest.services.responses;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ProductMinimumResponse {

    UUID sid;

    String name;

    Double price;

    Double discount;
}
