package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.UpdateProductUseCase;
import org.am.domain.catalog.Product;
import org.am.infrastructure.persistence.api.ProductDAO;

@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductDAO productDAO;

    @Override
    public Product update(final Product product) {

        return productDAO.update(product);
    }
}
