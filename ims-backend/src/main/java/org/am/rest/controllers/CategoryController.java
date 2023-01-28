package org.am.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.am.rest.RestConstants;
import org.am.rest.services.CategoryService;
import org.am.rest.services.requests.CategoryCreationRequest;
import org.am.rest.services.responses.CategoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryCreationRequest request) {

        return categoryService.create(request);
    }

    @GetMapping("/categories/{categorySid}")
    public CategoryResponse findBySid(@PathVariable UUID categorySid) {

        return categoryService.getBySid(categorySid);
    }

    @GetMapping("/categories")
    public List<CategoryResponse> get() {

        return categoryService.get();
    }
}
