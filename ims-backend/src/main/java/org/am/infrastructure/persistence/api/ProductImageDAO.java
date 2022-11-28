package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.ProductImage;

import java.util.List;

public interface ProductImageDAO {

    ProductImage persist(ProductImage productImage);

    List<ProductImage> persist(List<ProductImage> image);

    void delete(int imageId);
}
