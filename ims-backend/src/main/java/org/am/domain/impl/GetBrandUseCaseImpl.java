package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetBrandUseCase;
import org.am.domain.catalog.Brand;
import org.am.infrastructure.persistence.api.BrandDAO;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetBrandUseCaseImpl implements GetBrandUseCase {

    private final BrandDAO brandDAO;

    @Override
    public List<Brand> getBrands() {

        return brandDAO.findAll();
    }

    @Override
    public Brand getBySid(UUID brandSid) {

        return brandDAO.findBySid(brandSid);
    }
}
