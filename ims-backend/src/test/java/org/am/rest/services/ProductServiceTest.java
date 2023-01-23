package org.am.rest.services;

import org.am.domain.api.CreateProductImageUseCase;
import org.am.domain.api.CreateProductUseCase;
import org.am.domain.api.DeleteProductImageUseCase;
import org.am.domain.api.GetProductUseCase;
import org.am.domain.api.UpdateProductUseCase;
import org.am.domain.catalog.Product;
import org.am.fakers.Faker;
import org.am.rest.services.requests.ProductCreateRequest;
import org.am.rest.services.requests.converters.ProductFromProductCreateRequestConverter;
import org.am.rest.services.requests.converters.ProductFromProductUpdateRequestConverter;
import org.am.rest.services.requests.converters.ProductImageFromProductImageRequestConverter;
import org.am.rest.services.responses.ProductFullResponse;
import org.am.rest.services.responses.converters.ProductToFullResponseConverter;
import org.am.rest.services.responses.converters.ProductToMinimumResponseConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final Faker faker = new Faker();

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private GetProductUseCase getProductUseCase;

    @Mock
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private CreateProductImageUseCase createProductImageUseCase;

    @Mock
    private DeleteProductImageUseCase deleteProductImageUseCase;

    @Mock
    private ProductFromProductCreateRequestConverter productFromProductCreateRequestConverter;

    @Mock
    private ProductFromProductUpdateRequestConverter productFromProductUpdateRequestConverter;

    @Mock
    private ProductImageFromProductImageRequestConverter productImageFromProductImageRequestConverter;

    @Mock
    private ProductToFullResponseConverter productToFullResponseConverter;

    @Mock
    private ProductToMinimumResponseConverter productToMinimumResponseConverter;

    private final Supplier<ProductFullResponse> sProductMinimumResponse = () -> faker.domain.productFullResponse().build();

    @InjectMocks
    private ProductService subject;

    @Test
    void createProduct_whenUseCaseReturnsResult_convertResult() {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest().build();
        final Product product = faker.domain.product().build();
        final ProductFullResponse productFullResponse = sProductMinimumResponse.get();

        doReturn(product).when(productFromProductCreateRequestConverter).convert(eq(request));
        doReturn(product).when(createProductUseCase).create(eq(product));
        doReturn(productFullResponse).when(productToFullResponseConverter).convert(eq(product));

        // When
        final ProductFullResponse result = subject.createProduct(request);

        // Then
        assertThat(result).isEqualTo(productFullResponse);
    }
}
