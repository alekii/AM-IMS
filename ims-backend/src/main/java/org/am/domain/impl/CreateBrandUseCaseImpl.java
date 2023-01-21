package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateBrandUseCase;
import org.am.domain.catalog.Brand;
import org.am.infrastructure.persistence.api.BrandDAO;

@RequiredArgsConstructor
public class CreateBrandUseCaseImpl implements CreateBrandUseCase {

    private final BrandDAO brandDAO;

    @Override
    public Brand create(Brand brand) {

        return brandDAO.create(brand);
    }
}
