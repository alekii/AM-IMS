package org.am.rest.services.responses;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class PurchaseResponse {

    UUID sid;

    int invoice;

    Instant dateReceived;

    String receivedBy;

    Double totalAmount;

    SupplierResponse supplierResponse;

    @Value
    @lombok.Builder(builderClassName = "Builder")
    public static class SupplierResponse {

        UUID sid;

        String name;
    }

    List<ProductResponse> productResponse;

    @Value
    @lombok.Builder(builderClassName = "Builder")
    public static class ProductResponse {

        UUID sid;

        String name;

        Double price;
    }
}
