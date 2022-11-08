package org.am.infrastructure.products.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder(builderClassName = "builder")
public class ProductSearchResult {

    UUID sid;

    String name;

    String categoryName;

    String brandName;

    String receivedBy;

    Instant dateReceived;

    Double price;

    int quantity;

    Double discount;

    String description;

    String supplierName;
}
