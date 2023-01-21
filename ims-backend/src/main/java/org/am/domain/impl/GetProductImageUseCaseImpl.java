package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetProductImageUseCase;
import org.am.domain.catalog.ProductImage;
import org.am.infrastructure.persistence.api.ProductImageDAO;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetProductImageUseCaseImpl implements GetProductImageUseCase {

    private final ProductImageDAO productImageDAO;

    @Override
    public List<ProductImage> findAllByProductSid(UUID productSid) {

        return productImageDAO.findAllByProductSid(productSid);
    }

    @Override
    public ProductImage findBySid(UUID imageSid) {

        return productImageDAO.findBySid(imageSid);
    }
}
