package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryDAO {

    Category create(final Category category);

    List<Category> findAll();

    Category findBySid(final UUID categorySid);
}
