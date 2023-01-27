package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateCategoryUseCase;
import org.am.domain.api.GetCategoryUseCase;
import org.am.domain.catalog.Category;
import org.am.rest.services.requests.CategoryCreationRequest;
import org.am.rest.services.requests.converters.CategoryFromCreationRequestConverter;
import org.am.rest.services.responses.CategoryResponse;
import org.am.rest.services.responses.converters.CategoryResponseConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CreateCategoryUseCase createCategoryUseCase;

    private final GetCategoryUseCase getCategoryUseCase;

    private final CategoryResponseConverter CategoryResponseConverter;

    private final CategoryFromCreationRequestConverter CategoryFromCreationRequestConverter;

    public List<CategoryResponse> get() {

        return getCategoryUseCase.getCategories().stream()
                .map(CategoryResponseConverter::convert)
                .collect(Collectors.toUnmodifiableList());
    }

    public CategoryResponse getBySid(UUID sid) {

        final Category Category = getCategoryUseCase.getBySid(sid);

        return CategoryResponseConverter.convert(Category);
    }

    public CategoryResponse create(CategoryCreationRequest request) {

        final Category Category = CategoryFromCreationRequestConverter.convert(request);
        return CategoryResponseConverter.convert(createCategoryUseCase.create(Category));
    }
}
