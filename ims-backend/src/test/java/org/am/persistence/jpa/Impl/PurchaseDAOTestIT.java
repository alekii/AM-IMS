package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.exceptions.NotFound.PurchaseNotFoundException;
import org.am.infrastructure.persistence.converters.ProductEntityToProductConverter;
import org.am.infrastructure.persistence.impl.PurchaseDAOImpl;
import org.am.infrastructure.products.ProductRepository;
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
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PurchaseDAOTestIT extends BaseIntegrationTest {

    @Autowired
    private PurchaseDAOImpl subject;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    private List<Product> products;

    private WarehouseEntity savedWarehouseEntity;

    private SupplierEntity savedSupplierEntity;

    private PurchaseProductEntity lineItem;

    @Autowired
    private ProductEntityToProductConverter productConverter;

    @BeforeEach
    void init() {

        products = List.of(faker.domain.product().build(), faker.domain.product().build());
        lineItem = faker.entity.lineItems().build();
        savedWarehouseEntity = integrationTestPersister.save(faker.entity.warehouse().build());
        savedSupplierEntity = integrationTestPersister.save(faker.entity.supplier().build());
    }

    @Test
    void create_createsLineItems_thenReturnsCreatedPurchaseOrder() {

        // Given
        final Purchase purchase = buildPurchase(savedSupplierEntity);

        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final List<ProductEntity> productEntity = buildProductsEntityList(products, savedSupplierEntity, brandEntity, categoryEntity);
        for (int i = 0; i < purchase.getProducts().size(); i++) {
            final ProductEntity savedProductEntity = integrationTestPersister.save(productEntity.get(i));
            purchase.getProducts().get(i).setId(savedProductEntity.getId());
        }

        // When
        final Purchase createdPurchase = subject.create(purchase);

        // Then
        assertThat(createdPurchase.getSid()).isEqualTo(purchase.getSid());
        final Optional<PurchaseEntity> persistedPurchase = purchaseRepository.findBySid(createdPurchase.getSid());
        assertThat(persistedPurchase).isPresent();
        assertThat(persistedPurchase.get().getLineItems()).hasSize(2);

        assertThat(createdPurchase.getTotalAmount()).isEqualTo(getTotalAmountOfProducts(products));
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
        final PurchaseEntity purchaseEntity = buildPurchaseEntity(savedSupplierEntity);
        purchaseEntity.setLineItems(Collections.singletonList(lineItem));
        integrationTestPersister.save(purchaseEntity);

        // When
        final PurchaseEntity persistedPurchase = subject.findBySid(purchaseEntity.getSid());

        // Then
        assertThat(persistedPurchase.getSid()).isEqualTo(purchaseEntity.getSid());
    }

    @Test
    void findBySid_whenPurchaseDoesNotExist_throwsPurchaseNotFoundException() {

        // Given
        final UUID purchaseSid = UUID.randomUUID();

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> subject.findBySid(purchaseSid);

        // Then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(PurchaseNotFoundException.class);
    }

    @Test
    void findAll_whenPurchasesExist_returnsPurchasesList() {

        // Given
        final PurchaseEntity purchaseEntity = buildPurchaseEntity(savedSupplierEntity);
        purchaseEntity.setLineItems(Collections.singletonList(lineItem));
        integrationTestPersister.save(purchaseEntity);

        // When
        final List<Purchase> result = subject.findAll();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).extracting(Purchase::getSid).containsExactly(purchaseEntity.getSid());
    }

    @Test
    void updatePurchase_whenPurchaseIsPresent_returnsPurchaseWithUpdatedProductsAndStatus() {

        // Given
        final Purchase purchase = buildPurchase(savedSupplierEntity);

        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final List<ProductEntity> productEntity = buildProductsEntityList(products, savedSupplierEntity, brandEntity, categoryEntity);
        for (int i = 0; i < purchase.getProducts().size(); i++) {
            final ProductEntity savedProductEntity = integrationTestPersister.save(productEntity.get(i));
            purchase.getProducts().get(i).setId(savedProductEntity.getId());
        }
        subject.create(purchase);

        final Optional<PurchaseEntity> persistedPurchaseEntity = purchaseRepository.findBySid(purchase.getSid());
        assertThat(persistedPurchaseEntity).isNotNull();

        final List<Product> productsDelivered = persistedPurchaseEntity.get().getLineItems().stream()
                .map(lineItem -> productConverter.convert(lineItem.getProduct()))
                .collect(Collectors.toList());

        // When
        purchase.setStatus(PurchaseStatus.FULFILLED);
        productsDelivered.remove(1);
        purchase.setProducts(productsDelivered);
        final Purchase updatedPurchase = subject.update(purchase);

        // Then
        assertThat(updatedPurchase.getStatus()).isEqualTo(PurchaseStatus.FULFILLED);

        final PurchaseEntity updatedPurchaseEntity = purchaseRepository.findBySid(purchase.getSid()).get();
        final List<Product> persistedProducts = updatedPurchaseEntity.getLineItems().stream()
                .map(lineItem -> productConverter.convert(lineItem.getProduct()))
                .collect(Collectors.toList());

        assertThat(persistedProducts).usingRecursiveComparison().isEqualTo(productsDelivered);
    }

    private ProductEntity buildProductEntity(final Product product,
                                             final SupplierEntity supplierEntity,
                                             final BrandEntity brand,
                                             final CategoryEntity category) {

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

    private PurchaseEntity buildPurchaseEntity(SupplierEntity savedSupplierEntity) {

        return PurchaseEntity.builder()
                .sid(UUID.randomUUID())
                .invoiceNumber(0)
                .billValue(0.00)
                .status(PurchaseStatus.PENDING_APPROVAL)
                .supplier(savedSupplierEntity)
                .warehouseSid(UUID.randomUUID())
                .build();
    }

    private Purchase buildPurchase(SupplierEntity savedSupplierEntity) {

        return Purchase.builder()
                .sid(UUID.randomUUID())
                .invoice(0)
                .totalAmount(0.00)
                .status(PurchaseStatus.PENDING_APPROVAL)
                .supplier(Supplier.builder()
                                  .sid(savedSupplierEntity.getSid())
                                  .build())
                .warehouseSid(UUID.randomUUID())
                .products(products)
                .build();
    }
}
