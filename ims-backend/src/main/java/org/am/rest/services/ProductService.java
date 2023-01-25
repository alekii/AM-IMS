package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateProductImageUseCase;
import org.am.domain.api.CreateProductUseCase;
import org.am.domain.api.DeleteProductImageUseCase;
import org.am.domain.api.GetProductImageUseCase;
import org.am.domain.api.GetProductUseCase;
import org.am.domain.api.UpdateProductUseCase;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.ProductImage;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CreateProductUseCase createProductUseCase;

    private final CreateProductImageUseCase createProductImageUseCase;

    private final GetProductUseCase getProductUseCase;

    private final GetProductImageUseCase getProductImageUseCase;

    private final DeleteProductImageUseCase deleteProductImageUseCase;

    private final UpdateProductUseCase updateProductUseCase;

    private final ProductFromProductCreateRequestConverter productFromProductCreateRequestConverter;

    private final ProductToFullResponseConverter productToFullResponseConverter;

    private final ProductFromProductUpdateRequestConverter productFromProductUpdateRequestConverter;

    private final ProductImageFromProductImageRequestConverter productImageFromProductImageRequestConverter;

    private final ProductImageToProductImageResponseConverter productImageToProductImageResponseConverter;

    public ProductFullResponse createProduct(ProductCreateRequest request) {

        final Product product = productFromProductCreateRequestConverter.convert(request);

        return productToFullResponseConverter.convert(createProductUseCase.create(product));
    }

    public ProductFullResponse findBySid(UUID productSid) {

        return productToFullResponseConverter.convert(getProductUseCase.getBySid(productSid));
    }

    public ProductFullResponse updateProduct(ProductUpdateRequest request) {

        final Product product = productFromProductUpdateRequestConverter.convert(request);

        return productToFullResponseConverter.convert(updateProductUseCase.update(product));
    }

    public ProductImageResponse addProductImage(ProductImageCreateRequest request) {

        final ProductImage productImage = productImageFromProductImageRequestConverter.convert(request);
        return productImageToProductImageResponseConverter.convert(createProductImageUseCase.create(productImage));
    }

    public void deleteProductImage(int imageId) {

        deleteProductImageUseCase.delete(imageId);
    }

    public ProductImageResponse findByImageSid(UUID imageSid) {

        return productImageToProductImageResponseConverter.convert(getProductImageUseCase.findBySid(imageSid));
    }

    public List<ProductImageResponse> getProductImages(UUID productSid) {

        return getProductImageUseCase.findAllByProductSid(productSid)
                .stream()
                .map(productImageToProductImageResponseConverter::convert)
                .collect(Collectors.toList());
    }
}
