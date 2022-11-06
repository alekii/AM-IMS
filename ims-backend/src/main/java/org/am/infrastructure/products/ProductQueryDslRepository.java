package org.am.infrastructure.products;

import org.am.library.entities.ProductEntity;
import org.am.library.entities.SupplierEntity;

import java.util.List;
import java.util.UUID;

public interface ProductQueryDslRepository {

    List<ProductEntity> findProductByCategory(final UUID categorySid);

    List<ProductEntity> findProductByBrand(final UUID brandSID);

    List<ProductEntity> findProductByPrice(Double minimum, Double maximum);

    List<ProductEntity> findProductBySupplier();

    List<SupplierEntity> findSuppliersForProduct(final UUID productSid);
}
