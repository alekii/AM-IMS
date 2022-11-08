package org.am.domain.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductSearchResultModel {

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
