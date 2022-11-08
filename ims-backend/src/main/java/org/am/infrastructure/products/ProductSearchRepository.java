package org.am.infrastructure.products;

import lombok.RequiredArgsConstructor;
import org.am.infrastructure.products.dto.ProductSearchResult;
import org.am.infrastructure.products.queries.ProductSearchQuery;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    final RowMapper<ProductSearchResult> rowMapper;

    public List<ProductSearchResult> findBy(final ProductSearchQuery query) {

        return jdbcTemplate.query(query.getSql(), query.getSqlParameterSource(), rowMapper);
    }
}
