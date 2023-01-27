package org.am.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.am.rest.RestConstants;
import org.am.rest.services.ProductService;
import org.am.rest.services.requests.ProductCreateRequest;
import org.am.rest.services.responses.ProductFullResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
