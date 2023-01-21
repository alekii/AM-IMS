package org.am.domain.api;

import org.am.domain.catalog.ProductImage;

import java.util.List;

public interface CreateProductImageUseCase {

    ProductImage create(ProductImage image);

    List<ProductImage> create(List<ProductImage> images);
}
