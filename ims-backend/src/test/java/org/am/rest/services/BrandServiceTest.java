package org.am.rest.services;

import org.am.domain.api.CreateBrandUseCase;
import org.am.domain.api.GetBrandUseCase;
import org.am.domain.catalog.Brand;
import org.am.domain.catalog.exceptions.NotFound.BrandNotFoundException;
import org.am.fakers.Faker;
import org.am.rest.services.requests.BrandCreationRequest;
import org.am.rest.services.requests.converters.BrandFromCreationRequestConverter;
import org.am.rest.services.responses.BrandResponse;
import org.am.rest.services.responses.converters.BrandResponseConverter;
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
public class BrandServiceTest {

    private final Faker faker = new Faker();

    @Mock
    private GetBrandUseCase getBrandUseCase;

    @Mock
    private CreateBrandUseCase createBrandUseCase;

    @Mock
    private BrandResponseConverter brandResponseConverter;

    @Mock
    private BrandFromCreationRequestConverter brandFromCreationRequestConverter;

    @InjectMocks
    private BrandService brandService;

    @Test
    void getBrands_WhenBrandsExist_thenReturnBrandResponseList() {

        // Given
        final Brand brand = faker.domain.brand().build();
        final BrandResponse brandResponse = faker.domain.brandResponse().build();
        doReturn(List.of(brand)).when(getBrandUseCase).getBrands();
        doReturn(brandResponse).when(brandResponseConverter).convert(eq(brand));

        // When
        brandService.get();

        // Then
        verify(getBrandUseCase).getBrands();
        verify(brandResponseConverter).convert(eq(brand));
    }

    @Test
    void getByBrandSid_whenBrandExists_thenBrandResponse() {

        // Given
        final Brand brand = faker.domain.brand().build();
        final UUID sid = UUID.randomUUID();
        final BrandResponse brandResponse = faker.domain.brandResponse().build();

        doReturn(brand).when(getBrandUseCase).getBySid(any());
        doReturn(brandResponse).when(brandResponseConverter).convert(eq(brand));

        // When
        brandService.getBySid(sid);

        // Then
        verify(getBrandUseCase).getBySid(any());
        verify(brandResponseConverter).convert(eq(brand));
    }

    @Test
    void getBySid_whenBrandDoesNotExist_throwsBrandNotFoundException() {

        // Given
        final Brand brand = faker.domain.brand().build();

        doThrow(BrandNotFoundException.forSid(UUID.randomUUID()))
                .when(getBrandUseCase)
                .getBySid(any());

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> brandService.getBySid(brand.getSid());

        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(BrandNotFoundException.class);
    }

    @Test
    void create_callsCreateBrandUseCaseAndBrandConverters() {

        // Given
        final BrandCreationRequest request = faker.domain.brandCreationRequest().build();

        // When
        brandService.create(request);

        // Then
        verify(brandFromCreationRequestConverter).convert(eq(request));
        verify(createBrandUseCase).create(any());
        verify(brandResponseConverter).convert(any());
    }
}
