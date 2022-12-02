package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.exceptions.NotFound.CategoryNotFoundException;
import org.am.domain.catalog.exceptions.conflicts.CategoryAlreadyExistsException;
import org.am.infrastructure.category.CategoryRepository;
import org.am.infrastructure.persistence.api.CategoryDAO;
import org.am.infrastructure.persistence.converters.CategoryConverter;
import org.am.infrastructure.persistence.converters.CategoryEntityConverter;
import org.am.library.entities.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryDAOImpl implements CategoryDAO {

    private final CategoryRepository categoryRepository;

    private final CategoryEntityConverter categoryEntityConverter;

    private final CategoryConverter categoryConverter;

    @Override
    public Category create(Category category) {

        categoryRepository.findByName(category.getName())
                .ifPresent(categoryEntity -> {
                    throw CategoryAlreadyExistsException.forName(categoryEntity.getName());
                });
        final CategoryEntity categoryEntity = categoryRepository.save(categoryEntityConverter.convert(category));
        return categoryConverter.convert(categoryEntity);
    }

    @Override
    public List<Category> findAll() {

        return categoryRepository.findAll().stream()
                .map(categoryConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Category findBySid(final UUID categorySid) {

        final CategoryEntity categoryEntity = categoryRepository
                .findBySid(categorySid)
                .orElseThrow(() -> CategoryNotFoundException.forSid(categorySid));

        return categoryConverter.convert(categoryEntity);
    }
}
