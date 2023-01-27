package org.am.rest.services;

import org.am.domain.api.CreateCategoryUseCase;
import org.am.domain.api.GetCategoryUseCase;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.exceptions.NotFound.CategoryNotFoundException;
import org.am.fakers.Faker;
import org.am.rest.services.requests.CategoryCreationRequest;
import org.am.rest.services.requests.converters.CategoryFromCreationRequestConverter;
import org.am.rest.services.responses.CategoryResponse;
import org.am.rest.services.responses.converters.CategoryResponseConverter;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private final Faker faker = new Faker();

    @Mock
    private GetCategoryUseCase getCategoryUseCase;

    @Mock
    private CreateCategoryUseCase createCategoryUseCase;

    @Mock
    private CategoryResponseConverter categoryResponseConverter;

    @Mock
    private CategoryFromCreationRequestConverter categoryFromCreationRequestConverter;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getCategories_WhenCategoriesExist_thenReturnBrandResponseList() {

        // Given
        final Category brand = faker.domain.category().build();
        final CategoryResponse brandResponse = faker.domain.categoryResponse().build();
        doReturn(List.of(brand)).when(getCategoryUseCase).getCategories();
        doReturn(brandResponse).when(categoryResponseConverter).convert(eq(brand));

        // When
        categoryService.get();

        // Then
        verify(getCategoryUseCase).getCategories();
        verify(categoryResponseConverter).convert(eq(brand));
    }

    @Test
    void getByCategorySid_whenCategoryExists_thenCategoryResponse() {

        // Given
        final Category brand = faker.domain.category().build();
        final UUID sid = UUID.randomUUID();
        final CategoryResponse brandResponse = faker.domain.categoryResponse().build();

        doReturn(brand).when(getCategoryUseCase).getBySid(any());
        doReturn(brandResponse).when(categoryResponseConverter).convert(eq(brand));

        // When
        categoryService.getBySid(sid);

        // Then
        verify(getCategoryUseCase).getBySid(any());
        verify(categoryResponseConverter).convert(eq(brand));
    }

    @Test
    void getBySid_whenCategoryDoesNotExist_throwsCategoryNotFoundException() {

        // Given
        final Category brand = faker.domain.category().build();

        doThrow(CategoryNotFoundException.forSid(UUID.randomUUID()))
                .when(getCategoryUseCase)
                .getBySid(any());

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> categoryService.getBySid(brand.getSid());

        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void create_callsCreateCategoryUseCaseAndCategoryConverters() {

        // Given
        final CategoryCreationRequest request = faker.domain.categoryCreationRequest().build();

        // When
        categoryService.create(request);

        // Then
        verify(categoryFromCreationRequestConverter).convert(eq(request));
        verify(createCategoryUseCase).create(any());
        verify(categoryResponseConverter).convert(any());
    }
}
