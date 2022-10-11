package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Supplier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierDAO {

    Supplier create(final Supplier supplier);

    List<Supplier> findAll();

    Optional<Supplier> findBySid(final UUID supplierSid);

    Optional<Supplier> updateSupplier(final Supplier supplier);
}
