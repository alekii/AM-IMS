package org.am.rest.services.responses.converters;

import org.am.domain.catalog.ProductImage;
import org.am.rest.services.responses.ProductImageResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductImageToProductImageResponseConverter {

    public ProductImageResponse convert(final ProductImage productImage) {

        return ProductImageResponse.builder()
                .id(productImage.getId())
                .imagePath(productImage.getImagePath())
                .build();
    }
}
