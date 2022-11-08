package org.am.infrastructure.products;

import org.am.library.entities.SupplierEntity;

import java.util.List;
import java.util.UUID;

public interface ProductQueryDslRepository {

    List<SupplierEntity> findSuppliersForProduct(final UUID productSid);
}
