package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreateSupplierUseCase;
import org.am.domain.api.GetSupplierUseCase;
import org.am.domain.api.UpdateSupplierUseCase;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.requests.SupplierCreateRequest;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.am.rest.services.requests.converters.SupplierFromSupplierCreateRequest;
import org.am.rest.services.requests.converters.SupplierFromSupplierUpdateRequest;
import org.am.rest.services.responses.SupplierResponse;
import org.am.rest.services.responses.converters.SupplierResponseConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final CreateSupplierUseCase createSupplierUseCase;

    private final UpdateSupplierUseCase updateSupplierUseCase;

    private final GetSupplierUseCase getSupplierUseCase;

    private final SupplierResponseConverter supplierResponseConverter;

    private final SupplierFromSupplierCreateRequest supplierFromSupplierCreateRequest;

    private final SupplierFromSupplierUpdateRequest supplierFromSupplierUpdateRequest;

    public SupplierResponse create(final SupplierCreateRequest request) {

        final Supplier supplier = supplierFromSupplierCreateRequest.convert(request);

        return supplierResponseConverter.convert(createSupplierUseCase.create(supplier));
    }

    public SupplierResponse findBySid(final UUID supplierSid) {

        return supplierResponseConverter.convert(getSupplierUseCase.getSupplierBySid(supplierSid));
    }

    public List<SupplierResponse> findAll() {

        return getSupplierUseCase.getSuppliers()
                .stream()
                .map(supplierResponseConverter::convert)
                .collect(Collectors.toList());
    }

    public SupplierResponse update(final SupplierUpdateRequest request, final UUID supplierSid) {

        final Supplier supplier = supplierFromSupplierUpdateRequest.convert(request, supplierSid);

        return supplierResponseConverter.convert(updateSupplierUseCase.update(supplier));
    }
}
