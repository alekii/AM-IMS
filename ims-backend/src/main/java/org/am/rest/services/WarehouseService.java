package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateWarehouseUseCase;
import org.am.domain.api.GetWarehouseUseCase;
import org.am.domain.api.UpdateWarehouseUseCase;
import org.am.domain.catalog.Warehouse;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.am.rest.services.requests.WarehouseUpdateRequest;
import org.am.rest.services.requests.converters.WarehouseFromWarehouseCreateRequestConverter;
import org.am.rest.services.requests.converters.WarehouseFromWarehouseUpdateRequestConverter;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.am.rest.services.responses.WarehouseMinimumResponse;
import org.am.rest.services.responses.converters.WarehouseModelToFullResponseConverter;
import org.am.rest.services.responses.converters.WarehouseModelToMinimumResponseConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final GetWarehouseUseCase getWarehouseUseCase;

    private final CreateWarehouseUseCase createWarehouseUseCase;

    private final UpdateWarehouseUseCase updateWarehouseUseCase;

    private final WarehouseFromWarehouseCreateRequestConverter warehouseFromWarehouseCreateRequestConverter;

    private final WarehouseFromWarehouseUpdateRequestConverter warehouseFromWarehouseUpdateRequestConverter;

    private final WarehouseModelToFullResponseConverter warehouseModelToFullResponseConverter;

    private final WarehouseModelToMinimumResponseConverter warehouseModelToMinimumResponseConverter;

    public List<WarehouseMinimumResponse> getWarehouses() {

        return getWarehouseUseCase.getWarehouses()
                .stream()
                .map(warehouseModelToMinimumResponseConverter::convert)
                .collect(Collectors.toList());
    }

    public WarehouseFullResponse findBySid(UUID warehouseSid) {

        return warehouseModelToFullResponseConverter.convert(getWarehouseUseCase.getBySid(warehouseSid));
    }

    public WarehouseMinimumResponse create(WarehouseCreateRequest request) {

        final Warehouse warehouse = warehouseFromWarehouseCreateRequestConverter.convert(request);

        return warehouseModelToMinimumResponseConverter.convert(createWarehouseUseCase.create(warehouse));
    }

    public WarehouseFullResponse update(final WarehouseUpdateRequest warehouseUpdateRequest, final UUID warehouseSid) {

        final Warehouse warehouse = warehouseFromWarehouseUpdateRequestConverter.convert(warehouseUpdateRequest, warehouseSid);

        return warehouseModelToFullResponseConverter.convert(updateWarehouseUseCase.update(warehouse));
    }
}
