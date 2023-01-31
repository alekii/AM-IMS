package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.exceptions.NotFound.ProductNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.PurchaseNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.SupplierNotFoundException;
import org.am.infrastructure.lineItems.LineItemsRepository;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.converters.ProductEntityToProductConverter;
import org.am.infrastructure.persistence.converters.PurchaseEntityToPurchaseConverter;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.am.infrastructure.products.ProductRepository;
import org.am.infrastructure.purchases.PurchaseRepository;
import org.am.infrastructure.suppliers.SuppplierRepository;
import org.am.library.entities.ProductEntity;
import org.am.library.entities.PurchaseEntity;
import org.am.library.entities.PurchaseProductEntity;
import org.am.library.entities.SupplierEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchaseDAOImpl implements PurchaseDAO {

    private final PurchaseRepository purchasesRepository;

    private final SuppplierRepository supplierRepository;

    private final ProductRepository productRepository;

    private final LineItemsRepository lineItemsRepository;

    private final PurchaseEntityToPurchaseConverter purchaseEntityConverter;

    private final ProductEntityToProductConverter productConverter;

    private final PurchaseToPurchaseEntityConverter purchaseToPurchaseEntityConverter;

    @Override
    @Transactional
    public Purchase create(final Purchase purchase) {

        // Save transient
        SupplierEntity supplierEntity = validateAndGetSupplier(purchase.getSupplier());
        supplierRepository.save(supplierEntity);

        final PurchaseEntity purchaseEntity = purchaseToPurchaseEntityConverter.convert(purchase);
        purchaseEntity.setSupplier(supplierEntity);

        // Get persisted purchase with status pending
        final PurchaseEntity persistedPurchaseEntity = purchasesRepository.save(purchaseEntity);

        return createLineItem(persistedPurchaseEntity, purchase.getProducts());
    }

    private Purchase createLineItem(final PurchaseEntity purchase, final List<Product> products) {

        List<PurchaseProductEntity> lineItems = products.stream()
                .map(product -> lineItemsRepository.save(buildLineItem(purchase, product)))
                .collect(Collectors.toList());

        purchase.setLineItems(lineItems);
        updateTotalPurchasesAmount(purchase);
        Purchase result = purchaseEntityConverter.convert(purchasesRepository.save(purchase));

        return buildProductsList(lineItems, result);
    }

    private Purchase buildProductsList(List<PurchaseProductEntity> lineItems, Purchase result) {

        List<Product> productList = lineItems.stream()
                .map(PurchaseProductEntity::getProduct)
                .map(productConverter::convert)
                .collect(Collectors.toList());

        result.setProducts(productList);
        return result;
    }

    private void updateTotalPurchasesAmount(final PurchaseEntity purchase) {

        purchase.setBillValue(0.00);

        for (PurchaseProductEntity lineItem : purchase.getLineItems()) {
            purchase.setBillValue(purchase.getBillValue() + lineItem.getPrice());
        }
    }

    private PurchaseProductEntity buildLineItem(final PurchaseEntity purchaseEntity, final Product product) {

        ProductEntity productEntity = productRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException(String.format("No Product found for id %d", product.getId())));

        return PurchaseProductEntity.builder()
                .sid(UUID.randomUUID())
                .price(productEntity.getPrice())
                .quantity(productEntity.getQuantity())
                .product(productEntity)
                .purchase(purchaseEntity)
                .build();
    }

    @Override
    public PurchaseEntity findBySid(final UUID sid) {

        return purchasesRepository.findBySid(sid)
                .orElseThrow(() -> PurchaseNotFoundException.forSid(sid));
    }

    @Override
    public List<Purchase> findAll() {

        return purchasesRepository.fetchAll()
                .stream()
                .map(purchaseEntityConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Purchase update(final Purchase purchase) {

        final PurchaseEntity purchasePersisted = findBySid(purchase.getSid());

        updatePurchaseProducts(purchase, purchasePersisted);

        updateTotalPurchasesAmount(purchasePersisted);

        purchasePersisted.setStatus(purchase.getStatus());
        final Purchase createdPurchase = purchaseEntityConverter.convert(purchasesRepository.save(purchasePersisted));
        return buildProductsList(purchasePersisted.getLineItems(), createdPurchase);
    }

    private void updatePurchaseProducts(final Purchase purchase, PurchaseEntity purchasePersisted) {

        Set<Integer> existingSet = purchasePersisted.getLineItems().stream()
                .map(lineItem -> lineItem.getProduct().getId())
                .collect(Collectors.toSet());

        Set<Integer> newSet = purchase.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        existingSet.removeAll(newSet);

        existingSet.forEach(lineItemsRepository::deleteByProductId);
        purchasePersisted.setLineItems(lineItemsRepository.findByPurchaseId(purchasePersisted.getId()));
    }

    private SupplierEntity validateAndGetSupplier(Supplier supplier) {

        return supplierRepository.findBySid(supplier.getSid())
                .orElseThrow(() -> SupplierNotFoundException.forSid(supplier.getSid()));
    }
}
