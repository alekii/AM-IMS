package org.am.rest.services;

import org.am.domain.api.CreateProductImageUseCase;
import org.am.domain.api.CreateProductUseCase;
import org.am.domain.api.DeleteProductImageUseCase;
import org.am.domain.api.GetProductUseCase;
import org.am.domain.api.UpdateProductUseCase;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.ProductImage;
import org.am.fakers.Faker;
import org.am.rest.services.requests.ProductCreateRequest;
import org.am.rest.services.requests.ProductImageCreateRequest;
import org.am.rest.services.requests.ProductUpdateRequest;
import org.am.rest.services.requests.converters.ProductFromProductCreateRequestConverter;
import org.am.rest.services.requests.converters.ProductFromProductUpdateRequestConverter;
import org.am.rest.services.requests.converters.ProductImageFromProductImageRequestConverter;
import org.am.rest.services.responses.ProductFullResponse;
import org.am.rest.services.responses.ProductImageResponse;
import org.am.rest.services.responses.converters.ProductImageToProductImageResponseConverter;
import org.am.rest.services.responses.converters.ProductToFullResponseConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    private ProductImageToProductImageResponseConverter productImageToProductImageResponseConverter;

    @Mock
    private ProductFromProductUpdateRequestConverter productFromProductUpdateRequestConverter;

    @Mock
    private ProductImageFromProductImageRequestConverter productImageFromProductImageRequestConverter;

    @Mock
    private ProductToFullResponseConverter productToFullResponseConverter;

    private final Supplier<ProductFullResponse> sProductFullResponse = () -> faker.domain.productFullResponse().build();

    private final Supplier<ProductImageResponse> sProductImageResponse = () -> faker.domain.productImageResponse().build();

    private final Supplier<Product> sProduct = () -> faker.domain.product().build();

    @InjectMocks
    private ProductService subject;

    @Test
    void createProduct_whenUseCaseReturnsResult_convertResult() {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest().build();
        final Product product = faker.domain.product().build();
        final ProductFullResponse productFullResponse = sProductFullResponse.get();

        doReturn(product).when(productFromProductCreateRequestConverter).convert(eq(request));
        doReturn(product).when(createProductUseCase).create(eq(product));
        doReturn(productFullResponse).when(productToFullResponseConverter).convert(eq(product));

        // When
        final ProductFullResponse result = subject.createProduct(request);

        // Then
        assertThat(result).isEqualTo(productFullResponse);
    }

    @Test
    void findBySid_whenProductExists_returnsProductSuccessfully() {

        // Given
        final Product product = sProduct.get();
        final ProductFullResponse productFullResponse = sProductFullResponse.get();

        doReturn(product)
                .when(getProductUseCase)
                .getBySid(any(UUID.class));

        doReturn(productFullResponse)
                .when(productToFullResponseConverter)
                .convert(any(Product.class));

        // When
        final ProductFullResponse result = subject.findBySid(product.getSid());

        // Then
        assertThat(result.getSid()).isEqualTo(productFullResponse.getSid());
    }

    @Test
    void updateProduct_whenUseCaseReturnsResult_convertResult() {

        // Given
        final ProductUpdateRequest request = faker.domain.productUpdateRequest().build();
        final Product product = faker.domain.product().build();
        final ProductFullResponse productFullResponse = sProductFullResponse.get();

        doReturn(product).when(productFromProductUpdateRequestConverter).convert(eq(request));
        doReturn(product).when(updateProductUseCase).update(eq(product));
        doReturn(productFullResponse).when(productToFullResponseConverter).convert(eq(product));

        // When
        final ProductFullResponse result = subject.updateProduct(request);

        // Then
        assertThat(result).isEqualTo(productFullResponse);
    }

    @Test
    void create_ProductImage_whenUseCaseReturnsResult_thenConverts() {

        // Given
        final ProductImageCreateRequest request = faker.domain.productImageCreateRequest().build();
        final ProductImage productImage = faker.domain.productImage().build();
        final ProductImageResponse productImageResponse = sProductImageResponse.get();

        doReturn(productImage).when(productImageFromProductImageRequestConverter).convert(eq(request));
        doReturn(productImage).when(createProductImageUseCase).create(eq(productImage));
        doReturn(productImageResponse).when(productImageToProductImageResponseConverter).convert(eq(productImage));

        // When
        final ProductImageResponse result = subject.addProductImage(request);

        // Then
        assertThat(result).isEqualTo(productImageResponse);
    }
}
