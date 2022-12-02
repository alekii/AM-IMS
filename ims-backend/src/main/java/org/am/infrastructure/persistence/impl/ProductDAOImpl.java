package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.exceptions.NotFound.BrandNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.CategoryNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.ProductNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.SupplierNotFoundException;
import org.am.domain.catalog.exceptions.conflicts.ProductAlreadyExistsException;
import org.am.infrastructure.brand.BrandRepository;
import org.am.infrastructure.category.CategoryRepository;
import org.am.infrastructure.persistence.api.ProductDAO;
import org.am.infrastructure.persistence.converters.ProductEntityToProductConverter;
import org.am.infrastructure.persistence.converters.ProductToProductEntityConverter;
import org.am.infrastructure.products.ProductRepository;
import org.am.infrastructure.suppliers.SuppplierRepository;
import org.am.library.entities.BrandEntity;
import org.am.library.entities.CategoryEntity;
import org.am.library.entities.ProductEntity;
import org.am.library.entities.SupplierEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final SuppplierRepository suppplierRepository;

    private final BrandRepository brandRepository;

    private final ProductToProductEntityConverter productEntityConverter;

    private final ProductEntityToProductConverter productConverter;

    @Override
    @Transactional
    public Product create(Product product) {

        checkIfProductExists(product);

        final ProductEntity productEntity = productEntityConverter.convert(product);
        productEntity.setBrand(getBrandEntityBySid(product.getBrand().getSid()));
        productEntity.setCategory(getCategoryEntityBySid(product.getCategory().getSid()));
        productEntity.setSupplied_by(getSupplierEntityBySid(product.getSupplier().getSid()));
        return productConverter.convert(productRepository.save(productEntity));
    }

    private void checkIfProductExists(final Product product) {

        productRepository.findByName(product.getName())
                .ifPresent(productEntity -> {
                    throw ProductAlreadyExistsException.forName(productEntity.getName());
                });
    }

    private CategoryEntity getCategoryEntityBySid(final UUID categorySid) {

        return categoryRepository.findBySid(categorySid)
                .orElseThrow(() -> CategoryNotFoundException.forSid(categorySid));
    }

    private BrandEntity getBrandEntityBySid(final UUID brandSid) {

        return brandRepository.findBySid(brandSid)
                .orElseThrow(() -> BrandNotFoundException.forSid(brandSid));
    }

    private SupplierEntity getSupplierEntityBySid(final UUID supplierSid) {

        return suppplierRepository.findBySid(supplierSid)
                .orElseThrow(() -> SupplierNotFoundException.forSid(supplierSid));
    }

    @Override
    public List<Product> findAll() {

        return productRepository.findAll()
                .stream()
                .map(productConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Product findBySid(final UUID productSid) {

        return productRepository.findBySid(productSid)
                .map(productConverter::convert)
                .orElseThrow(() -> ProductNotFoundException.forSid(productSid));
    }

    @Override
    public Product update(final Product product) {

        final UUID productSid = product.getSid();
        ProductEntity persistedProductEntity = productRepository.findBySid(productSid)
                .orElseThrow(() -> ProductNotFoundException.forSid(productSid));

        final ProductEntity productEntityToUpdate = productEntityConverter.convert(product);
        productEntityToUpdate.setId(persistedProductEntity.getId());
        productEntityToUpdate.setBrand(getBrandEntityBySid(product.getBrand().getSid()));
        productEntityToUpdate.setCategory(getCategoryEntityBySid(product.getCategory().getSid()));
        productEntityToUpdate.setSupplied_by(getSupplierEntityBySid(product.getSupplier().getSid()));
        productEntityToUpdate.setDate_received(persistedProductEntity.getDate_received());
        productEntityToUpdate.setReceived_by(persistedProductEntity.getReceived_by());
        productEntityToUpdate.setWarehouseSid(persistedProductEntity.getWarehouseSid());
        return productConverter.convert(productRepository.save(productEntityToUpdate));
    }
}
