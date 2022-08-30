package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetWarehouseUseCase;
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
}
