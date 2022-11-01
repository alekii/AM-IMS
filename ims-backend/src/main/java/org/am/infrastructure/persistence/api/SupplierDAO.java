package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierDAO {

    Supplier create(final Supplier supplier);

    List<Supplier> findAll();

    Supplier findBySid(final UUID supplierSid);

    Supplier updateSupplier(final Supplier supplier);
}
