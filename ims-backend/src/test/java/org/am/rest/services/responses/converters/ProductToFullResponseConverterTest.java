package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Product;
import org.am.fakers.Faker;
import org.am.rest.services.responses.ProductFullResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductToFullResponseConverterTest {

    private final Faker faker = new Faker();

    private final ProductToFullResponseConverter productToFullResponseConverter
            = new ProductToFullResponseConverter();

    @Test
    void convert_Warehouse_returnsProductToProductFullResponse() {

        // Given
        final Product product = faker.domain.product().build();
        final ProductFullResponse productFullResponse = buildProductFullResponse(product);

        // When
        final ProductFullResponse response = productToFullResponseConverter.convert(product);

        // Then
        assertThat(productFullResponse).usingRecursiveComparison().isEqualTo(response);
    }

    private ProductFullResponse buildProductFullResponse(Product product) {

        return ProductFullResponse.builder()
                .sid(product.getSid())
                .brand(ProductFullResponse.BrandResponse.builder()
                               .sid(product.getBrand().getSid())
                               .name(product.getBrand().getName())
                               .build())
                .category(ProductFullResponse.CategoryResponse.builder()
                                  .sid(product.getCategory().getSid())
                                  .name(product.getCategory().getName())
                                  .build())
                .supplier(ProductFullResponse.SupplierResponse.builder()
                                  .sid(product.getSupplier().getSid())
                                  .name(product.getSupplier().getName())
                                  .build())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .quantity(product.getQuantity())
                .warehouseSid(product.getWarehouseSid())
                .receivedBy(product.getReceivedBy())
                .dateReceived(product.getDateReceived())
                .sku(product.getSku())
                .description(product.getDescription())
                .name(product.getName())
                .build();
    }
}
