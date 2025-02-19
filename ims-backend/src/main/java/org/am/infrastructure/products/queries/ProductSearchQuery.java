package org.am.infrastructure.products.queries;

import lombok.Builder;
import lombok.Getter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder(builderClassName = "Builder")
public class ProductSearchQuery {

    public static final String SEARCH_TEXT_PARAMETER = "searchText";

    public static final String BRAND_NAME_PARAMETER = "brandName";

    public static final String CATEGORY_NAME_PARAMETER = "categoryName";

    public static final String MIN_PRICE_PARAMETER = "minPrice";

    public static final String MAX_PRICE_PARAMETER = "maxPrice";

    public static final String LIMIT_PARAMETER = "limit";

    public static final String ORDER_BY_PARAMETER = "orderBy";

    public static final String SUPPLIER_NAME_PARAMETER = "supplierName";

    public static final String WAREHOUSE_SEARCH_PARAMETER = "warehouseSid";

    public static final String DATE_RECEIVED_SEARCH_PARAMETER = "dateReceived";

    private static final String EMPTY_STRING = "";

    private static final String SUPPLIER_WHERE_CLAUSE = "AND supplier.name =:supplierName\n";

    private static final String CATEGORY_WHERE_CLAUSE = "AND category.name =:categoryName\n";

    private static final String BRAND_WHERE_CLAUSE = "AND brand.name =:brandName\n";

    private static final String MIN_PRICE_WHERE_CLAUSE = "AND price >:minPrice\n";

    private static final String MIN_PRICE_DEFAULT_WHERE_CLAUSE = "AND price > 0.00\n";

    private static final String MAX_PRICE_DEFAULT_WHERE_CLAUSE = "AND price < 10000000.00\n";

    private static final String MAX_PRICE_WHERE_CLAUSE = "AND price <:maxPrice\n";

    private static final String DATE_RECEIVED_WHERE_CLAUSE = "AND date =:dateReceived\n";

    private static final String BASE_SELECT_CLAUSE =
            "SELECT products.sid, products.name, products.received_by, products.price,"
                    + "brands.name brand, categories.name category, suppliers.name supplier\n"
                    + "products.quantity, products.description FROM products\n"
                    + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                    + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                    + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n";

    private static final String BASE_WHERE_CLAUSE = "WHERE products.warehouse =:warehouseSid\n";

    private static final String SEARCH_TEXT_WHERE_CLAUSE = "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n";

    String searchText;

    String brandName;

    String categoryName;

    Double minPrice;

    Double maxPrice;

    int limit;

    String orderBy;

    String supplierName;

    UUID warehouseSid;

    Instant dateReceived;

    public String getSql() {

        return BASE_SELECT_CLAUSE +
                BASE_WHERE_CLAUSE +
                getSearchTextWhereClause() +
                getCategoryWhereClause() +
                getBrandWhereClause() +
                getMinPriceWhereClause() +
                getMaxPriceWhereClause() +
                getDateReceivedClause() +
                getSupplierWhereClause() +
                getOrderByAndLimit();
    }

    public SqlParameterSource getSqlParameterSource() {

        return new MapSqlParameterSource()
                .addValue(WAREHOUSE_SEARCH_PARAMETER, warehouseSid)
                .addValue(SEARCH_TEXT_PARAMETER, searchText)
                .addValue(CATEGORY_NAME_PARAMETER, categoryName)
                .addValue(BRAND_NAME_PARAMETER, brandName)
                .addValue(MIN_PRICE_PARAMETER, minPrice)
                .addValue(MAX_PRICE_PARAMETER, maxPrice)
                .addValue(SUPPLIER_NAME_PARAMETER, supplierName)
                .addValue(DATE_RECEIVED_SEARCH_PARAMETER, dateReceived)
                .addValue(LIMIT_PARAMETER, limit)
                .addValue(ORDER_BY_PARAMETER, orderBy);
    }

    private String getSearchTextWhereClause() {

        return searchText == null ? EMPTY_STRING : SEARCH_TEXT_WHERE_CLAUSE;
    }

    private String getMaxPriceWhereClause() {

        return maxPrice == null ? MAX_PRICE_DEFAULT_WHERE_CLAUSE : MAX_PRICE_WHERE_CLAUSE;
    }

    private String getMinPriceWhereClause() {

        return minPrice == null ? MIN_PRICE_DEFAULT_WHERE_CLAUSE : MIN_PRICE_WHERE_CLAUSE;
    }

    private String getBrandWhereClause() {

        return brandName == null ? EMPTY_STRING : BRAND_WHERE_CLAUSE;
    }

    private String getCategoryWhereClause() {

        return categoryName == null ? EMPTY_STRING : CATEGORY_WHERE_CLAUSE;
    }

    private String getSupplierWhereClause() {

        return supplierName == null ? EMPTY_STRING : SUPPLIER_WHERE_CLAUSE;
    }

    private String getOrderByAndLimit() {

        StringBuilder builder = new StringBuilder("ORDER BY ");

        switch (orderBy) {
            case "price":
                builder.append("products.price ASC\n");
                break;
            case "name":
                builder.append("products.name ASC\n");
                break;
            default:
                builder.append("products.id ASC\n");
        }

        builder.append("LIMIT :limit");

        return builder.toString();
    }

    private String getDateReceivedClause() {

        return dateReceived == null ? EMPTY_STRING : DATE_RECEIVED_WHERE_CLAUSE;
    }
}
