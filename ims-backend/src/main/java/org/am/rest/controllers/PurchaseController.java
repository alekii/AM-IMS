package org.am.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.am.rest.RestConstants;
import org.am.rest.services.PurchaseService;
import org.am.rest.services.requests.PurchaseCreateRequest;
import org.am.rest.services.requests.PurchaseUpdateRequest;
import org.am.rest.services.responses.PurchaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(RestConstants.API_ENDPOINT)
@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/purchases")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PurchaseResponse create(@Valid @RequestBody PurchaseCreateRequest request) {

        return purchaseService.create(request);
    }

    @PatchMapping("/purchases/{purchaseSid}")
    public PurchaseResponse update(
            @PathVariable UUID purchaseSid,
            @Valid @RequestBody PurchaseUpdateRequest request) {

        return purchaseService.update(request, purchaseSid);
    }

    @GetMapping("/purchases/{purchaseSid}")
    public PurchaseResponse findBySid(@PathVariable UUID purchaseSid) {

        return purchaseService.getBySid(purchaseSid);
    }
}
