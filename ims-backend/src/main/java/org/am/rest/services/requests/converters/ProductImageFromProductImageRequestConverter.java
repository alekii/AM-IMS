package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.ProductImage;
import org.am.rest.services.requests.ProductImageCreateRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductImageFromProductImageRequestConverter implements Converter<ProductImageCreateRequest, ProductImage> {

    @Override
    public ProductImage convert(ProductImageCreateRequest source) {

        return ProductImage.builder()
                .sid(UUID.randomUUID())
                .imagePath(source.getImagePath())
                .productSid(source.getProductSid())
                .build();
    }
}
