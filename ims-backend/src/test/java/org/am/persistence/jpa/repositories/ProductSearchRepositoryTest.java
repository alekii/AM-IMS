package org.am.persistence.jpa.repositories;

import org.am.fakers.Faker;
import org.am.infrastructure.products.ProductSearchRepository;
import org.am.infrastructure.products.mappers.ProductSearchResultRowMapper;
import org.am.infrastructure.products.queries.ProductSearchQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductSearchRepositoryTest {

    private Faker faker = new Faker();

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private ProductSearchResultRowMapper rowMapper;

    @InjectMocks
    private ProductSearchRepository productSearchRepository;

    @Test
    void findAll_preparesProductSearchQuery() {

        // Given
        final ProductSearchQuery productSearchQuery = buildProductSearchQuery();

        // When
        productSearchRepository.findBy(productSearchQuery);

        // Then
        verify(jdbcTemplate).query(eq(productSearchQuery.getSql()),
                                   any(MapSqlParameterSource.class),
                                   eq(rowMapper));
    }

    private ProductSearchQuery buildProductSearchQuery() {

        return ProductSearchQuery.builder()
                .searchText(null)
                .brandName("sony")
                .categoryName("category")
                .warehouseSid(UUID.randomUUID())
                .supplierName("XB Suppliers")
                .minPrice(0.00)
                .maxPrice(434.00)
                .dateReceived(Instant.now())
                .orderBy("price")
                .limit(1)
                .build();
    }
}

