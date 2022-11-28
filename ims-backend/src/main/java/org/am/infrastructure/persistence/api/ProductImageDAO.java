package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.ProductImage;

public interface ProductImageDAO {

    ProductImage persist(ProductImage productImage);
}
