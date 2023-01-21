package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Product;
import org.am.rest.services.responses.ProductMinimumResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductToMinimumResponseConverter {

    public ProductMinimumResponse convert(final Product product) {

        return ProductMinimumResponse.builder()
                .sid(product.getSid())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getPrice())
                .build();
    }
}
