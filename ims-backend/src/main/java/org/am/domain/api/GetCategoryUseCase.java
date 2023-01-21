package org.am.domain.api;

import org.am.domain.catalog.Category;

import java.util.List;
import java.util.UUID;

public interface GetCategoryUseCase {

    List<Category> getCategories();

    Category getBySid(UUID categorySid);
}
