package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateCategoryUseCase;
import org.am.domain.catalog.Category;
import org.am.infrastructure.persistence.api.CategoryDAO;

@RequiredArgsConstructor
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

    private final CategoryDAO categoryDAO;

    @Override
    public Category create(Category category) {

        return categoryDAO.create(category);
    }
}
