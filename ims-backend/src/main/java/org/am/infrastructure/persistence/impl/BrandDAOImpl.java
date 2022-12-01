package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Brand;
import org.am.domain.catalog.exceptions.NotFound.BrandNotFoundException;
import org.am.domain.catalog.exceptions.conflicts.BrandAlreadyExistsException;
import org.am.infrastructure.brand.BrandRepository;
import org.am.infrastructure.persistence.api.BrandDAO;
import org.am.infrastructure.persistence.converters.BrandConverter;
import org.am.infrastructure.persistence.converters.BrandEntityConverter;
import org.am.library.entities.BrandEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BrandDAOImpl implements BrandDAO {

    private final BrandRepository brandRepository;

    private final BrandConverter brandConverter;

    private final BrandEntityConverter brandEntityConverter;

    @Override
    public Brand create(Brand brand) {

        String brandName = brand.getName();
        brandRepository.findByName(brandName)
                .ifPresent(brandEntity -> {
                    throw BrandAlreadyExistsException.forName(brandName);
                });

        final BrandEntity brandEntity = brandRepository.save(brandEntityConverter.convert(brand));
        return brandConverter.convert(brandEntity);
    }

    @Override
    public List<Brand> findAll() {

        return brandRepository.findAll().stream()
                .map(brandConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Brand findBySid(UUID brandSid) {

        final BrandEntity brandEntity = brandRepository
                .findBySid(brandSid)
                .orElseThrow(() -> BrandNotFoundException.forSid(brandSid));

        return brandConverter.convert(brandEntity);
    }
}
