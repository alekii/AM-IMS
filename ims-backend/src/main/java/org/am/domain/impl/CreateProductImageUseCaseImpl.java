package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateProductImageUseCase;
import org.am.domain.catalog.ProductImage;
import org.am.infrastructure.persistence.api.ProductImageDAO;

import java.util.List;

@RequiredArgsConstructor
public class CreateProductImageUseCaseImpl implements CreateProductImageUseCase {

    private final ProductImageDAO productImageDAO;

    @Override
    public ProductImage create(ProductImage image) {

        return productImageDAO.persist(image);
    }

    @Override
    public List<ProductImage> createImages(List<ProductImage> images) {

        return productImageDAO.persist(images);
    }
}
