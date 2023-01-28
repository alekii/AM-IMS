package org.am.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.am.rest.RestConstants;
import org.am.rest.services.ProductService;
import org.am.rest.services.requests.ProductCreateRequest;
import org.am.rest.services.requests.ProductImageCreateRequest;
import org.am.rest.services.requests.ProductUpdateRequest;
import org.am.rest.services.responses.ProductFullResponse;
import org.am.rest.services.responses.ProductImageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(RestConstants.API_ENDPOINT)
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductFullResponse create(@Valid @RequestBody ProductCreateRequest request) {

        return productService.createProduct(request);
    }

    @PatchMapping("/products")
    public ProductFullResponse update(@Valid @RequestBody ProductUpdateRequest request) {

        return productService.updateProduct(request);
    }

    @GetMapping("/products/{productSid}")
    public ProductFullResponse getProductBySid(@PathVariable UUID productSid) {

        return productService.findBySid(productSid);
    }

    @PostMapping("/products/images")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductImageResponse create(@Valid @RequestBody ProductImageCreateRequest request) {

        return productService.addProductImage(request);
    }

    @GetMapping("/products/images/{imageSid}")
    public ProductImageResponse findImageBySid(@PathVariable UUID imageSid) {

        return productService.findByImageSid(imageSid);
    }

    @GetMapping("/products/{productSid}/images")
    public List<ProductImageResponse> getImagesForProduct(@PathVariable UUID productSid) {

        return productService.getProductImages(productSid);
    }

    @DeleteMapping("/products/images/{imageId}")
    public void deleteImageFromProduct(@PathVariable int imageId) {

        productService.deleteProductImage(imageId);
    }
}
