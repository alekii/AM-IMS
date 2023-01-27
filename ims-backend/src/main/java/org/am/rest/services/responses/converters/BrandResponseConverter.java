package org.am.rest.services.responses.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Brand;
import org.am.rest.services.responses.BrandResponse;
import org.springframework.stereotype.Component;

@Component
public class BrandResponseConverter implements Converter<Brand, BrandResponse> {

    @Override
    public BrandResponse convert(Brand source) {

        return BrandResponse.builder()
                .sid(source.getSid())
                .name(source.getName())
                .build();
    }
}
