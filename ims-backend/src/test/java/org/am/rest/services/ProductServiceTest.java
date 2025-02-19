package org.am.rest.services;

import org.am.domain.api.CreateProductImageUseCase;
import org.am.domain.api.CreateProductUseCase;
import org.am.domain.api.DeleteProductImageUseCase;
import org.am.domain.api.GetProductImageUseCase;
import org.am.domain.api.GetProductUseCase;
import org.am.domain.api.UpdateProductUseCase;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.ProductImage;
import org.am.domain.catalog.exceptions.NotFound.ProductImageNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.ProductNotFoundException;
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
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
    private GetProductImageUseCase getProductImageUseCase;

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
    void findBySid_whenProductDoesNotExist_throwsProductNotFoundException() {

        // Given
        final Product product = faker.domain.product().build();
        doThrow(ProductNotFoundException.forSid(UUID.randomUUID()))
                .when(getProductUseCase)
                .getBySid(any());

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> subject.findBySid(product.getSid());

        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(ProductNotFoundException.class);
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

    @Test
    void create_ProductImage_whenProductIsInvalid_throwsProductNotFoundException() {

        // Given
        final ProductImageCreateRequest request = faker.domain.productImageCreateRequest().build();

        doThrow(ProductNotFoundException.forSid(UUID.randomUUID()))
                .when(createProductImageUseCase)
                .create(any());

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> subject.addProductImage(request);

        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void delete_calls_deleteUseCase() {

        // Given
        final ProductImage image = faker.domain.productImage().build();
        final int imageId = image.getId();
        doNothing().when(deleteProductImageUseCase).delete(imageId);

        // When
        subject.deleteProductImage(imageId);

        //Then
        verify(deleteProductImageUseCase).delete(eq(imageId));
    }

    @Test
    void getImageForProduct_whenProductImageExists_returnImagesList() {

        //Given
        final ProductImage image = faker.domain.productImage().build();
        doReturn(List.of(image)).when(getProductImageUseCase).findAllByProductSid(any(UUID.class));

        //When
        subject.getProductImages(UUID.randomUUID());

        // Then
        verify(getProductImageUseCase).findAllByProductSid(any());
    }

    @Test
    void getProductImages_whenProductImageNotExist_returnEmptyList() {

        // Given
        final List<ProductImage> productImages = new ArrayList<>();

        doReturn(productImages)
                .when(getProductImageUseCase)
                .findAllByProductSid(any(UUID.class));

        // When
        final List<ProductImageResponse> result = subject.getProductImages(UUID.randomUUID());

        //Then
        verify(productImageToProductImageResponseConverter, never()).convert(any());

        assertThat(result).isEmpty();
    }

    @Test
    void findBySid_whenProductImageExists_returnsProductImageSuccessfully() {

        // Given
        final ProductImage image = faker.domain.productImage().build();
        final ProductImageResponse response = faker.domain.productImageResponse().build();

        doReturn(image)
                .when(getProductImageUseCase)
                .findBySid(any(UUID.class));

        doReturn(response)
                .when(productImageToProductImageResponseConverter)
                .convert(any(ProductImage.class));

        // When
        final ProductImageResponse result = subject.findByImageSid(image.getSid());

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(response);
    }

    @Test
    void findBySid_whenProductImageDoesNotExist_throwsProductImageNotFoundException() {

        // Given
        final ProductImage image = faker.domain.productImage().build();
        doThrow(ProductImageNotFoundException.forSid(UUID.randomUUID()))
                .when(getProductImageUseCase)
                .findBySid(any());

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> subject.findByImageSid(image.getSid());

        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(ProductImageNotFoundException.class);
    }
}
