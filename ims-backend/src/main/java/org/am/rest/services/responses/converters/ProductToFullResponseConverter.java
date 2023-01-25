package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Brand;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.responses.ProductFullResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductToFullResponseConverter {

    public ProductFullResponse convert(final Product product) {

        return ProductFullResponse.builder()
                .sid(product.getSid())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .warehouseSid(product.getWarehouseSid())
                .receivedBy(product.getReceivedBy())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .sku(product.getSku())
                .dateReceived(product.getDateReceived())
                .dateReceived(product.getDateReceived())
                .brand(buildBrandResponse(product.getBrand()))
                .category(buildCategoryResponse(product.getCategory()))
                .supplier(buildSupplierResponse(product.getSupplier()))
                .build();
    }

    private ProductFullResponse.SupplierResponse buildSupplierResponse(Supplier supplier) {

        return ProductFullResponse.SupplierResponse.builder()
                .sid(supplier.getSid())
                .name(supplier.getName())
                .build();
    }

    private ProductFullResponse.CategoryResponse buildCategoryResponse(Category category) {

        return ProductFullResponse.CategoryResponse.builder()
                .sid(category.getSid())
                .name(category.getName())
                .build();
    }

    private ProductFullResponse.BrandResponse buildBrandResponse(Brand brand) {

        return ProductFullResponse.BrandResponse.builder()
                .sid(brand.getSid())
                .name(brand.getName())
                .build();
    }
}
