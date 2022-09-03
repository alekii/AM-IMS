package org.am.rest.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.am.rest.RestConstants;
import org.am.rest.services.WarehouseService;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.am.rest.services.responses.WarehouseMinimumResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(RestConstants.API_ENDPOINT)
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses")
    @ApiOperation(value = "Find all Warehouses.")
    public List<WarehouseMinimumResponse> getWarehouses() {

        return warehouseService.getWarehouses();
    }

    @GetMapping("/warehouses/{warehouseSid}")
    @ApiOperation(value = "Find Warehouse by SID.")
    public WarehouseFullResponse findBySid(
            @Valid @PathVariable final UUID warehouseSid) {

        return warehouseService.findBySid(warehouseSid);
    }

    @PostMapping("/warehouses/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Create warehouse")
    public WarehouseMinimumResponse createWarehouse(
            @Valid @RequestBody WarehouseCreateRequest warehouseCreationRequest) {

        return warehouseService.create(warehouseCreationRequest);
    }
}
