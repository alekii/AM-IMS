package org.am.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.am.rest.RestConstants;
import org.am.rest.services.SupplierService;
import org.am.rest.services.requests.SupplierCreateRequest;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.am.rest.services.responses.SupplierResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(RestConstants.API_ENDPOINT)
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/suppliers/all")
    public ResponseEntity<List<SupplierResponse>> getSuppliers() {

        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/suppliers/{supplierSid}")
    public ResponseEntity<SupplierResponse> findBySid(
            @Valid @PathVariable final UUID supplierSid) {

        return new ResponseEntity<>(supplierService.findBySid(supplierSid), HttpStatus.OK);
    }

    @PostMapping("/suppliers/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SupplierResponse> createSupplier(
            @Valid @RequestBody SupplierCreateRequest supplierCreateRequest) {

        SupplierResponse supplierResponse = supplierService.create(supplierCreateRequest);
        return new ResponseEntity<>(supplierResponse, HttpStatus.CREATED);
    }

    @PutMapping("/suppliers/{supplierSid}")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @Valid @RequestBody final SupplierUpdateRequest supplierUpdateRequest,
            @Valid @PathVariable final UUID supplierSid) {

        SupplierResponse SupplierResponse = supplierService.update(supplierUpdateRequest, supplierSid);
        return ResponseEntity.ok(SupplierResponse);
    }
}
