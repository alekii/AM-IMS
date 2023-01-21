package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.DeleteProductImageUseCase;
import org.am.infrastructure.persistence.api.ProductImageDAO;

@RequiredArgsConstructor
public class DeleteProductImageUseCaseImpl implements DeleteProductImageUseCase {

    private final ProductImageDAO productImageDAO;

    @Override
    public void delete(int imageId) {

        productImageDAO.delete(imageId);
    }
}
