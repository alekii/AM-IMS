package org.am.domain.catalog;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder(builderClassName = "Builder")
public class Product {

    int id;

    UUID sid;

    String name;

    Category category;

    Brand brand;

    String receivedBy;

    Instant dateReceived;

    Double price;

    int quantity;

    Double discount;

    String description;

    UUID warehouseSid;

    Supplier supplier;

    String sku;
}
