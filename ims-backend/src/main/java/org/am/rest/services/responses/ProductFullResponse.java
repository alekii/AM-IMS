package org.am.rest.services.responses;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class ProductFullResponse {

    UUID sid;

    String name;

    CategoryResponse category;

    BrandResponse brand;

    SupplierResponse supplier;

    @Value
    @Builder
    public static class CategoryResponse {

        UUID sid;

        String name;
    }

    @Value
    @Builder
    public static class BrandResponse {

        UUID sid;

        String name;
    }

    String receivedBy;

    Instant dateReceived;

    Double price;

    int quantity;

    Double discount;

    String description;

    UUID warehouseSid;

    @Value
    @Builder
    public static class SupplierResponse {

        UUID sid;

        String name;
    }

    String sku;
}
