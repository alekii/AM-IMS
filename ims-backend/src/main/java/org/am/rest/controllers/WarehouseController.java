package org.am.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.am.rest.RestConstants;
import org.am.rest.services.WarehouseService;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.am.rest.services.requests.WarehouseUpdateRequest;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.am.rest.services.responses.WarehouseMinimumResponse;
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
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses/all")
    public ResponseEntity<List<WarehouseMinimumResponse>> getWarehouses() {

        return new ResponseEntity<>(warehouseService.getWarehouses(), HttpStatus.OK);
    }

    @GetMapping("/warehouses/{warehouseSid}")
    public ResponseEntity<WarehouseFullResponse> findBySid(
            @Valid @PathVariable final UUID warehouseSid) {

        return new ResponseEntity<>(warehouseService.findBySid(warehouseSid), HttpStatus.OK);
    }

    @PostMapping("/warehouses/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<WarehouseMinimumResponse> createWarehouse(
            @Valid @RequestBody WarehouseCreateRequest warehouseCreationRequest) {

        WarehouseMinimumResponse warehouseMinimumResponse = warehouseService.create(warehouseCreationRequest);
        return new ResponseEntity<>(warehouseMinimumResponse, HttpStatus.CREATED);
    }

    @PutMapping("/warehouses/{warehouseSid}")
    public ResponseEntity<WarehouseFullResponse> updateWarehouse(
            @Valid @RequestBody final WarehouseUpdateRequest warehouseUpdateRequest,
            @Valid @PathVariable final UUID warehouseSid) {

        WarehouseFullResponse warehouseFullResponse = warehouseService.update(warehouseUpdateRequest, warehouseSid);
        return ResponseEntity.ok(warehouseFullResponse);
    }
}
