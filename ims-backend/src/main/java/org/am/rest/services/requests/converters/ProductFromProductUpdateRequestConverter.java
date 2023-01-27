package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Brand;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.Product;
import org.am.rest.services.requests.ProductUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductFromProductUpdateRequestConverter implements Converter<ProductUpdateRequest, Product> {

    @Override
    public Product convert(ProductUpdateRequest source) {

        return Product.builder()
                .sid(UUID.randomUUID())
                .discount(source.getDiscount())
                .price(source.getPrice())
                .brand(Brand.builder()
                               .sid(source.getBrand().getSid())
                               .name(source.getBrand().getName())
                               .build())
                .category(Category.builder()
                                  .sid(source.getCategory().getSid())
                                  .name(source.getCategory().getName())
                                  .build())
                .description(source.getDescription())
                .name(source.getName())
                .quantity(source.getQuantity())
                .sku(source.getSku())
                .build();
    }
}
