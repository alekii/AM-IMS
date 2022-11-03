package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateProductUseCase;
import org.am.domain.catalog.Product;
import org.am.infrastructure.persistence.api.ProductDAO;

@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductDAO productDAO;

    @Override
    public Product create(final Product product) {

        return productDAO.create(product);
    }
}
