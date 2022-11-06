package org.am.infrastructure.products.mappers;

import org.am.infrastructure.products.dto.ProductSearchResult;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

@Component
public class ProductSearchResultRowMapper implements RowMapper<ProductSearchResult> {

    @Override
    public ProductSearchResult mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ProductSearchResult.builder()
                .sid(rs.getObject("sid", UUID.class))
                .name(rs.getString("name"))
                .price(rs.getDouble("price"))
                .description(rs.getString("description"))
                .categoryName(rs.getString("columnName"))
                .brandName(rs.getString("brandName"))
                .dateReceived(rs.getObject("date_received", Date.class).toInstant())
                .supplierName(rs.getString("supplierName"))
                .receivedBy(rs.getString("received_by"))
                .quantity(rs.getInt("quantity"))
                .build();
    }
}
