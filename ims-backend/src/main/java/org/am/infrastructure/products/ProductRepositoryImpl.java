package org.am.infrastructure.products;

import org.am.library.entities.ProductEntity;
import org.am.library.entities.QBrandEntity;
import org.am.library.entities.QCategoryEntity;
import org.am.library.entities.SupplierEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.UUID;

import static org.am.library.entities.QProductEntity.productEntity;

public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductQueryDslRepository {

    private final ProductSearchRepository productSearchRepository;

    public ProductRepositoryImpl(ProductSearchRepository productSearchRepository) {

        super(ProductEntity.class);
        this.productSearchRepository = productSearchRepository;
    }

    @Override
    public List<ProductEntity> findProductByCategory(final UUID categorySid) {

        final QCategoryEntity qCategory = QCategoryEntity.categoryEntity;
        return from(productEntity)
                .innerJoin(productEntity.category, qCategory)
                .where(qCategory.sid.eq(categorySid))
                .fetch();
    }

    @Override
    public List<ProductEntity> findProductByBrand(final UUID brandSID) {

        final QBrandEntity qBrand = QBrandEntity.brandEntity;
        return from(productEntity)
                .innerJoin(productEntity.brand, qBrand)
                .where(qBrand.sid.eq(brandSID))
                .fetch();
    }

    @Override
    public List<ProductEntity> findProductByPrice(Double minimum, Double maximum) {

        return null;
    }

    @Override
    public List<ProductEntity> findProductBySupplier() {

        return null;
    }

    @Override
    public List<SupplierEntity> findSuppliersForProduct(UUID productSid) {

        return null;
    }
}
