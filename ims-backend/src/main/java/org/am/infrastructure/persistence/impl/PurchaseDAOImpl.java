package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.exceptions.NotFound.ProductNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.PurchaseNotFoundException;
import org.am.infrastructure.lineItems.LineItemsRepository;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.converters.ProductEntityToProductConverter;
import org.am.infrastructure.persistence.converters.PurchaseEntityToPurchaseConverter;
import org.am.infrastructure.products.ProductRepository;
import org.am.infrastructure.purchases.PurchaseRepository;
import org.am.library.entities.ProductEntity;
import org.am.library.entities.PurchaseEntity;
import org.am.library.entities.PurchaseProductEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchaseDAOImpl implements PurchaseDAO {

    private final PurchaseRepository purchasesRepository;

    private final ProductRepository productRepository;

    private final LineItemsRepository lineItemsRepository;

    private final PurchaseEntityToPurchaseConverter purchaseEntityConverter;

    private final ProductEntityToProductConverter productConverter;

    @Override
    @Transactional
    public Purchase create(final PurchaseEntity purchaseEntity, final List<Product> products) {

        // Get persisted purchase with status pending
        final PurchaseEntity persistedPurchaseEntity = purchasesRepository.save(purchaseEntity);

        return createLineItem(persistedPurchaseEntity, products);
    }

    private Purchase createLineItem(final PurchaseEntity purchase, final List<Product> products) {

        List<PurchaseProductEntity> lineItems = products.stream()
                .map(product ->
                             lineItemsRepository.save(
                                     buildLineItem(purchase, product))
                )
                .map(lineItem -> updateTotalPurchasesAmount(purchase, lineItem)).collect(Collectors.toList());

        purchase.setLineItems(lineItems);

        return purchaseEntityConverter.convert(purchasesRepository.save(purchase));
    }

    private PurchaseProductEntity updateTotalPurchasesAmount(final PurchaseEntity purchase, PurchaseProductEntity lineItem) {

        purchase.setBillValue(purchase.getBillValue() + lineItem.getPrice());

        return lineItem;
    }

    private PurchaseProductEntity buildLineItem(final PurchaseEntity purchaseEntity, final Product product) {

        ProductEntity productEntity = productRepository.findBySid(product.getSid())
                .orElseThrow(() -> ProductNotFoundException.forSid(product.getSid()));

        return PurchaseProductEntity.builder()
                .sid(UUID.randomUUID())
                .price(product.getPrice())
                .quantity(product.getQuantity())
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
    public Purchase update(final PurchaseEntity purchase, List<Product> products) {

        final PurchaseEntity purchasePersisted = findBySid(purchase.getSid());

        final PurchaseEntity purchaseToUpdate = updatePurchaseProducts(purchase, purchasePersisted, products);

        purchaseToUpdate.setStatus(purchase.getStatus());
        purchaseToUpdate.setInvoiceNumber(purchase.getInvoiceNumber());
        purchaseToUpdate.setDateReceived(purchase.getDateReceived());
        return purchaseEntityConverter.convert(purchasesRepository.save(purchaseToUpdate));
    }

    private PurchaseEntity updatePurchaseProducts(final PurchaseEntity purchase,
                                                  final PurchaseEntity purchasePersisted,
                                                  final List<Product> products) {

        Set<Product> existingSet = new HashSet<>();
        Set<Product> newSet = new HashSet<>(products);

        List<PurchaseProductEntity> lineItems = purchasePersisted.getLineItems();
        lineItems.forEach(v -> existingSet.add(productConverter.convert(v.getProduct())));
        existingSet.removeAll(newSet);
        existingSet.forEach(product -> lineItemsRepository.deleteByProductId(product.getId()));
        purchasePersisted.setLineItems(lineItemsRepository.findByPurchaseId(purchase.getId()));
        purchasesRepository.save(purchasePersisted);
        return findBySid(purchase.getSid());
    }
}
