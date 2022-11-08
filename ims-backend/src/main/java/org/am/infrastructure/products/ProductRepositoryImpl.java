package org.am.infrastructure.products;

import org.am.library.entities.ProductEntity;
import org.am.library.entities.QProductEntity;
import org.am.library.entities.SupplierEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.am.library.entities.QProductEntity.productEntity;
import static org.am.library.entities.QSupplierEntity.supplierEntity;

@Repository
public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductQueryDslRepository {

    public ProductRepositoryImpl() {

        super(ProductEntity.class);
    }

    @Override
    public List<SupplierEntity> findSuppliersForProduct(final UUID productSid) {

        final QProductEntity qProduct = productEntity;
        return from(supplierEntity)
                .innerJoin(supplierEntity.products, qProduct)
                .where(qProduct.sid.eq(productSid))
                .fetch();
    }
}
