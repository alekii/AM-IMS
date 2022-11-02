package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateSupplierUseCase;
import org.am.domain.api.GetSupplierUseCase;
import org.am.domain.api.UpdateSupplierUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final CreateSupplierUseCase createSupplierUseCase;

    private final UpdateSupplierUseCase updateSupplierUseCase;

    private final GetSupplierUseCase getSupplierUseCase;
}
