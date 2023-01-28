package org.am.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.am.rest.RestConstants;
import org.am.rest.services.BrandService;
import org.am.rest.services.requests.BrandCreationRequest;
import org.am.rest.services.responses.BrandResponse;
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
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/brands")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BrandResponse create(@Valid @RequestBody BrandCreationRequest request) {

        return brandService.create(request);
    }

    @GetMapping("/brands/{brandSid}")
    public BrandResponse findBySid(@PathVariable UUID brandSid) {

        return brandService.getBySid(brandSid);
    }

    @GetMapping("/brands")
    public List<BrandResponse> get() {

        return brandService.get();
    }
}
