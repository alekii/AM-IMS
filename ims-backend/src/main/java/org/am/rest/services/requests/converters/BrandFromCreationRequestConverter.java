package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Brand;
import org.am.rest.services.requests.BrandCreationRequest;

import java.util.UUID;

public class BrandFromCreationRequestConverter implements Converter<BrandCreationRequest, Brand> {

    @Override
    public Brand convert(BrandCreationRequest source) {

        return Brand.builder()
                .sid(UUID.randomUUID())
                .name(source.getName())
                .build();
    }
}
