package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.ProductImage;

import java.util.List;
import java.util.UUID;

public interface ProductImageDAO {

    ProductImage persist(ProductImage productImage);

    List<ProductImage> persist(List<ProductImage> images);

    List<ProductImage> findAllByProductSid(UUID productSid);

    ProductImage findBySid(UUID imageSid);

    void delete(int imageId);
}
