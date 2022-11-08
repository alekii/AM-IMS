package org.am.fakers;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.am.infrastructure.products.queries.ProductSearchQuery;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryFaker {

    private final Faker faker = new Faker();

    public ProductSearchQuery.Builder productSearchQuery() {

        return ProductSearchQuery.builder()
                .searchText("product")
                .brandName("sony")
                .categoryName("category")
                .warehouseSid(UUID.randomUUID())
                .supplierName("XB Suppliers")
                .minPrice(0.00)
                .maxPrice(434.00)
                .dateReceived(Instant.now())
                .orderBy("price")
                .limit(1);
    }
}
