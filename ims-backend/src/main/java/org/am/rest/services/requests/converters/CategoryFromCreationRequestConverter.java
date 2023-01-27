package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Category;
import org.am.rest.services.requests.CategoryCreationRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryFromCreationRequestConverter implements Converter<CategoryCreationRequest, Category> {

    @Override
    public Category convert(CategoryCreationRequest source) {

        return Category.builder()
                .sid(UUID.randomUUID())
                .name(source.getName())
                .build();
    }
}
