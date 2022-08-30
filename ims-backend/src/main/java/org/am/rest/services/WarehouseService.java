package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.GetWarehouseUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final GetWarehouseUseCase getWarehouseUseCase;
}
