package org.am.domain.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetCategoryUseCase;
import org.am.domain.catalog.Category;
import org.am.infrastructure.persistence.api.CategoryDAO;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetCategoryUseCaseImpl implements GetCategoryUseCase {

    private final CategoryDAO categoryDAO;

    @Override
    public List<Category> getCategories() {

        return categoryDAO.findAll();
    }

    @Override
    public Category getBySid(UUID categorySid) {

        return categoryDAO.findBySid(categorySid);
    }
}
