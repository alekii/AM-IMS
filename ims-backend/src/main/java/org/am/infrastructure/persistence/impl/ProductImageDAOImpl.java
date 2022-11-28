package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.ProductImage;
import org.am.infrastructure.persistence.api.ProductImageDAO;
import org.am.infrastructure.products.ProductRepository;
import org.am.library.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductImageDAOImpl implements ProductImageDAO {

    private final ProductRepository productRepository;

    @Override
    public ProductImage persist(ProductImage productImage) {

        final ProductEntity product = productRepository.findBySid(productImage.getProductSid())
                .orElseThrow(()-> Product)
    }
}
