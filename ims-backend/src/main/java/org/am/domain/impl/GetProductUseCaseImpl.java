package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetProductUseCase;
import org.am.domain.catalog.Product;
import org.am.infrastructure.persistence.api.ProductDAO;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetProductUseCaseImpl implements GetProductUseCase {

    private final ProductDAO productDAO;

    @Override
    public List<Product> getProducts() {

        return productDAO.findAll();
    }

    @Override
    public Product getBySid(UUID productSid) {

        return productDAO.findBySid(productSid);
    }
}
