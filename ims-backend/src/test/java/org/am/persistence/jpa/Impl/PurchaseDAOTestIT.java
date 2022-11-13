package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.infrastructure.persistence.impl.PurchaseDAOImpl;
import org.am.infrastructure.purchases.PurchaseRepository;
import org.am.library.entities.BrandEntity;
import org.am.library.entities.CategoryEntity;
import org.am.library.entities.ProductEntity;
import org.am.library.entities.PurchaseEntity;
import org.am.library.entities.PurchaseProductEntity;
import org.am.library.entities.SupplierEntity;
import org.am.library.entities.WarehouseEntity;
import org.am.library.entities.util.PurchaseStatus;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseDAOTestIT extends BaseIntegrationTest {

    @Autowired
    private PurchaseDAOImpl subject;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private List<Product> products;

    WarehouseEntity savedWarehouseEntity;

    SupplierEntity savedSupplierEntity;

    BrandEntity brandEntity;

    CategoryEntity categoryEntity;

    List<ProductEntity> productEntity;

    private PurchaseProductEntity lineItem;

    @BeforeEach
    void init() {

        products = List.of(faker.domain.product().build(), faker.domain.product().build());
        lineItem = faker.entity.lineItems().build();
        savedWarehouseEntity = integrationTestPersister.save(faker.entity.warehouse().build());
        savedSupplierEntity = integrationTestPersister.save(faker.entity.supplier().build());

        brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        productEntity = buildProductsEntityList(products, savedSupplierEntity, brandEntity, categoryEntity);
        productEntity.forEach(product -> integrationTestPersister.save(product));
    }

    @Test
    void create_createsLineItems_thenReturnsCreatedPurchaseOrder() {

        // Given
        final PurchaseEntity purchaseEntity = buildPurchase(savedSupplierEntity, savedWarehouseEntity);

        // When
        final Purchase purchase = subject.create(purchaseEntity, products);

        // Then
        assertThat(purchase.getSid()).isEqualTo(purchaseEntity.getSid());

        final Optional<PurchaseEntity> createdPurchase = purchaseRepository.findBySid(purchaseEntity.getSid());
        assertThat(createdPurchase).isPresent();
        assertThat(createdPurchase.get().getLineItems()).hasSize(2);

        final Double productsTotalAmount = getTotalAmountOfProducts(products);
        assertThat(purchase.getTotalAmount()).isEqualTo(productsTotalAmount);
    }

    private Double getTotalAmountOfProducts(List<Product> products) {

        Double totalAmount = 0.00;
        for (Product product : products) {
            totalAmount += product.getPrice();
        }
        return totalAmount;
    }

    private List<ProductEntity> buildProductsEntityList(List<Product> products,
                                                        SupplierEntity supplierEntity,
                                                        BrandEntity brand,
                                                        CategoryEntity category) {

        return products.stream()
                .map(product -> buildProductEntity(product, supplierEntity, brand, category))
                .collect(Collectors.toList());
    }

    @Test
    void findBySid_whenPurchaseExists_returnsPurchaseEntity() {

        // Given
        final PurchaseEntity purchaseEntity = buildPurchase(savedSupplierEntity, savedWarehouseEntity);
        purchaseEntity.setLineItems(Collections.singletonList(lineItem));
        integrationTestPersister.save(purchaseEntity);

        // When
        final PurchaseEntity persistedPurchase = subject.findBySid(purchaseEntity.getSid());

        // Then
        assertThat(persistedPurchase.getSid()).isEqualTo(purchaseEntity.getSid());
    }

    @Test
    void findAll_whenPurchasesExist_returnsPurchasesList() {

        // Given
        final PurchaseEntity purchaseEntity = buildPurchase(savedSupplierEntity, savedWarehouseEntity);
        purchaseEntity.setLineItems(Collections.singletonList(lineItem));
        integrationTestPersister.save(purchaseEntity);

        // When
        final List<Purchase> result = subject.findAll();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).extracting(Purchase::getSid).containsExactly(purchaseEntity.getSid());
    }

    @Test
    void updatePurchaseStatus_whenPurchaseIsPresent_returnsPurchaseWithUpdatedStatus() {

        // Given
        final PurchaseEntity purchaseEntity = buildPurchase(savedSupplierEntity, savedWarehouseEntity);

        final Purchase persistedPurchase = subject.create(purchaseEntity, products);

        final PurchaseEntity persistedPurchaseEntity = purchaseRepository.findBySid(purchaseEntity.getSid()).get();
        assertThat(persistedPurchaseEntity).isNotNull();

        persistedPurchaseEntity.setStatus(PurchaseStatus.FULFILLED);

        // When
        final Purchase updatedPurchase = subject.update(purchaseEntity, products);

        // Then
        assertThat(updatedPurchase.getStatus()).isEqualTo(persistedPurchaseEntity.getStatus());
    }

    private ProductEntity buildProductEntity(Product product, SupplierEntity supplierEntity, BrandEntity brand, CategoryEntity category) {

        return ProductEntity.builder()
                .name(product.getName())
                .sid(product.getSid())
                .warehouseSid(product.getWarehouseSid())
                .brand(brand)
                .price(product.getPrice())
                .category(category)
                .discount(product.getDiscount())
                .date_received(product.getDateReceived())
                .received_by(product.getReceivedBy())
                .supplied_by(supplierEntity)
                .sku(product.getSku())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .build();
    }

    private PurchaseEntity buildPurchase(SupplierEntity savedSupplierEntity, WarehouseEntity savedWarehouseEntity) {

        return PurchaseEntity.builder()
                .sid(UUID.randomUUID())
                .invoiceNumber(0)
                .billValue(0.00)
                .status(PurchaseStatus.PENDING_APPROVAL)
                .supplier(savedSupplierEntity)
                .warehouse(savedWarehouseEntity)
                .build();
    }
}
