package org.am.persistence.jpa.queries;

import org.am.fakers.Faker;
import org.am.infrastructure.products.queries.ProductSearchQuery;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductSearchQueryTest {

    private final Faker faker = new Faker();

    private final Supplier<ProductSearchQuery> productSearchQuerySupplier = () -> faker.query.productSearchQuery().build();

    @Test
    void getParameterSource_whenAllProductQueryParametersAreSet_setsAllSQLParameterSourceValues() {

        ProductSearchQuery productSearchQuery = productSearchQuerySupplier.get();
        final SqlParameterSource sqlParams = productSearchQuery.getSqlParameterSource();

        assertThat(
                sqlParams.getValue(ProductSearchQuery.SEARCH_TEXT_PARAMETER)
        ).isEqualTo(productSearchQuery.getSearchText());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.BRAND_NAME_PARAMETER)
        ).isEqualTo(productSearchQuery.getBrandName());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.CATEGORY_NAME_PARAMETER)
        ).isEqualTo(productSearchQuery.getCategoryName());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.SUPPLIER_NAME_PARAMETER)
        ).isEqualTo(productSearchQuery.getSupplierName());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.WAREHOUSE_SEARCH_PARAMETER)
        ).isEqualTo(productSearchQuery.getWarehouseSid());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.DATE_RECEIVED_SEARCH_PARAMETER)
        ).isEqualTo(productSearchQuery.getDateReceived());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.MAX_PRICE_PARAMETER)
        ).isEqualTo(productSearchQuery.getMaxPrice());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.MIN_PRICE_PARAMETER)
        ).isEqualTo(productSearchQuery.getMinPrice());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.ORDER_BY_PARAMETER)
        ).isEqualTo(productSearchQuery.getOrderBy());

        assertThat(
                sqlParams.getValue(ProductSearchQuery.LIMIT_PARAMETER)
        ).isEqualTo(productSearchQuery.getLimit());
    }

    @Test
    void getSQL_whenAllSQLParametersAreSet_setsJoinsAndWhereClauses() {

        assertThat(productSearchQuerySupplier.get().getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n"
                                   + "AND category.name =:categoryName\n"
                                   + "AND brand.name =:brandName\n"
                                   + "AND price >:minPrice\n"
                                   + "AND price <:maxPrice\n"
                                   + "AND date =:dateReceived\n"
                                   + "AND supplier.name =:supplierName\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }

    @Test
    void getSQL_whenSearchTextIsNull_SearchTextWhereClauseIsNotIncluded() {

        ProductSearchQuery productSearchQuery = faker.query.productSearchQuery().searchText(null).build();
        assertThat(productSearchQuery.getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND category.name =:categoryName\n"
                                   + "AND brand.name =:brandName\n"
                                   + "AND price >:minPrice\n"
                                   + "AND price <:maxPrice\n"
                                   + "AND date =:dateReceived\n"
                                   + "AND supplier.name =:supplierName\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }

    @Test
    void getSQL_whenBrandNameIsNull_BrandNameWhereClauseIsNotIncluded() {

        ProductSearchQuery productSearchQuery = faker.query.productSearchQuery().brandName(null).build();
        assertThat(productSearchQuery.getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n"
                                   + "AND category.name =:categoryName\n"
                                   + "AND price >:minPrice\n"
                                   + "AND price <:maxPrice\n"
                                   + "AND date =:dateReceived\n"
                                   + "AND supplier.name =:supplierName\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }

    @Test
    void getSQL_whenCategoryNameIsNull_CategoryNameWhereClauseIsNotIncluded() {

        ProductSearchQuery productSearchQuery = faker.query.productSearchQuery().categoryName(null).build();
        assertThat(productSearchQuery.getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n"
                                   + "AND brand.name =:brandName\n"
                                   + "AND price >:minPrice\n"
                                   + "AND price <:maxPrice\n"
                                   + "AND date =:dateReceived\n"
                                   + "AND supplier.name =:supplierName\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }

    @Test
    void getSQL_whenSupplierNameIsNull_supplierNameWhereClauseIsNotIncluded() {

        ProductSearchQuery productSearchQuery = faker.query.productSearchQuery().supplierName(null).build();
        assertThat(productSearchQuery.getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n"
                                   + "AND category.name =:categoryName\n"
                                   + "AND brand.name =:brandName\n"
                                   + "AND price >:minPrice\n"
                                   + "AND price <:maxPrice\n"
                                   + "AND date =:dateReceived\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }

    @Test
    void getSQL_whenMinPriceIsNull_defaultMinPriceWhereClauseIsIncluded() {

        ProductSearchQuery productSearchQuery = faker.query.productSearchQuery().minPrice(null).build();
        assertThat(productSearchQuery.getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n"
                                   + "AND category.name =:categoryName\n"
                                   + "AND brand.name =:brandName\n"
                                   + "AND price > 0.00\n"
                                   + "AND price <:maxPrice\n"
                                   + "AND date =:dateReceived\n"
                                   + "AND supplier.name =:supplierName\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }

    @Test
    void getSQL_whenMaxPriceIsNull_defaultMaxPriceWhereClauseIsIncluded() {

        ProductSearchQuery productSearchQuery = faker.query.productSearchQuery().maxPrice(null).build();
        assertThat(productSearchQuery.getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n"
                                   + "AND category.name =:categoryName\n"
                                   + "AND brand.name =:brandName\n"
                                   + "AND price >:minPrice\n"
                                   + "AND price < 10000000.00\n"
                                   + "AND date =:dateReceived\n"
                                   + "AND supplier.name =:supplierName\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }

    @Test
    void getSQL_whenMinPriceAndMaxPriceIsNull_defaultMinPriceAndMaxPriceIsIncludedInWhereClause() {

        ProductSearchQuery productSearchQuery = faker.query.productSearchQuery().minPrice(null).maxPrice(null).build();
        assertThat(productSearchQuery.getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n"
                                   + "AND category.name =:categoryName\n"
                                   + "AND brand.name =:brandName\n"
                                   + "AND price > 0.00\n"
                                   + "AND price < 10000000.00\n"
                                   + "AND date =:dateReceived\n"
                                   + "AND supplier.name =:supplierName\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }

    @Test
    void getSQL_whenDateReceivedIsNull_dateReceivedWhereClauseIsNotIncluded() {

        ProductSearchQuery productSearchQuery = faker.query.productSearchQuery().dateReceived(null).build();
        assertThat(productSearchQuery.getSql())
                .isEqualTo("SELECT products.sid, products.name, products.received_by, products.price,"
                                   + "brands.name brand, categories.name category, suppliers.name supplier\n"
                                   + "products.quantity, products.description FROM products\n"
                                   + "INNER JOIN brands ON brands.id_brand = products.fk_brand\n"
                                   + "INNER JOIN categories ON categories.id_category = products.fk_category\n"
                                   + "INNER JOIN suppliers ON suppliers.id_supplier = products.fk_supplier\n"
                                   + "WHERE products.warehouse =:warehouseSid\n"
                                   + "AND lower(products.name) LIKE lower('%' || :searchText || '%')\n"
                                   + "AND category.name =:categoryName\n"
                                   + "AND brand.name =:brandName\n"
                                   + "AND price >:minPrice\n"
                                   + "AND price <:maxPrice\n"
                                   + "AND supplier.name =:supplierName\n"
                                   + "ORDER BY products.price ASC\n"
                                   + "LIMIT :limit"
                );
    }
}
