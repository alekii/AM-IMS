package org.am.rest.services.responses.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Category;
import org.am.rest.services.responses.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryResponseConverter implements Converter<Category, CategoryResponse> {

    @Override
    public CategoryResponse convert(Category source) {

        return CategoryResponse.builder()
                .sid(source.getSid())
                .name(source.getName())
                .build();
    }
}
