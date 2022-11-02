package org.am.domain.api;

import org.am.domain.catalog.Supplier;

import java.util.List;
import java.util.UUID;

public interface GetSupplierUseCase {

    List<Supplier> getSuppliers();

    Supplier getSupplierBySid(UUID supplierSid);
}
