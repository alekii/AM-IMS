package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateBrandUseCase;
import org.am.domain.api.GetBrandUseCase;
import org.am.domain.catalog.Brand;
import org.am.rest.services.responses.BrandResponse;
import org.am.rest.services.responses.converters.BrandResponseConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final CreateBrandUseCase createBrandUseCase;

    private final GetBrandUseCase getBrandUseCase;

    private final BrandResponseConverter brandResponseConverter;

    public List<BrandResponse> get() {

        return getBrandUseCase.getBrands().stream()
                .map(brandResponseConverter::convert)
                .collect(Collectors.toUnmodifiableList());
    }

    public BrandResponse getBySid(UUID sid) {

        final Brand brand = getBrandUseCase.getBySid(sid);

        return brandResponseConverter.convert(brand);
    }
}
