package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.Brand;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.exceptions.NotFound.BrandNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.CategoryNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.ProductNotFoundException;
import org.am.domain.catalog.exceptions.NotFound.SupplierNotFoundException;
import org.am.infrastructure.brand.BrandRepository;
import org.am.infrastructure.category.CategoryRepository;
import org.am.infrastructure.persistence.impl.ProductDAOImpl;
import org.am.infrastructure.products.ProductRepository;
import org.am.library.entities.BrandEntity;
import org.am.library.entities.CategoryEntity;
import org.am.library.entities.ProductEntity;
import org.am.library.entities.SupplierEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductDAOTestIT extends BaseIntegrationTest {

    @Autowired
    private ProductDAOImpl subject;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void create_whenProductDoesNotExist_returnsPersistedProduct() {

        // Given
        final ProductEntity productEntity = faker.entity.product().build();
        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final SupplierEntity supplierEntity = integrationTestPersister.save(faker.entity.supplier().build());
        final Product expected = buildProduct(productEntity, categoryEntity, brandEntity, supplierEntity);

        // When
        final Product product = subject.create(expected);

        // Then
        assertThat(product.getSid()).isEqualTo(expected.getSid());

        final Optional<ProductEntity> createdProduct = productRepository.findBySid(productEntity.getSid());
        assertThat(createdProduct).isPresent();

        assertThat(product).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void createProduct_whenBrandDoesNotExist_throwsBrandNotFoundException() {

        // Given
        final ProductEntity productEntity = faker.entity.product().build();
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final SupplierEntity supplierEntity = integrationTestPersister.save(faker.entity.supplier().build());
        final Product expected = buildProductWithInvalidBrand(productEntity, categoryEntity, supplierEntity);

        // When
        final ThrowableAssert.ThrowingCallable savingWithInvalidBrand = () -> subject.create(expected);

        // Then
        assertThatThrownBy(savingWithInvalidBrand).isInstanceOf(BrandNotFoundException.class);
    }

    @Test
    void createProduct_whenCategoryDoesNotExist_throwsCategoryNotFoundException() {

        // Given
        final ProductEntity productEntity = faker.entity.product().build();
        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final SupplierEntity supplierEntity = integrationTestPersister.save(faker.entity.supplier().build());
        final Product expected = buildProductWithInvalidCategory(productEntity, brandEntity, supplierEntity);

        // When
        final ThrowableAssert.ThrowingCallable savingWithInvalidCategory = () -> subject.create(expected);

        // Then
        assertThatThrownBy(savingWithInvalidCategory).isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void createProduct_whenSupplierDoesNotExist_throwsSupplierNotFoundException() {

        // Given
        final ProductEntity productEntity = faker.entity.product().build();
        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final Product expected = buildProductWithInvalidSupplier(productEntity, categoryEntity, brandEntity);

        // When
        final ThrowableAssert.ThrowingCallable savingWithInvalidSupplier = () -> subject.create(expected);

        // Then
        assertThatThrownBy(savingWithInvalidSupplier).isInstanceOf(SupplierNotFoundException.class);
    }

    @Test
    void findBySid_whenProductExists_returnsProductEntity() {

        // Given
        final ProductEntity entity = faker.entity.product().build();

        integrationTestPersister.save(entity);

        // When
        final Product result = subject.findBySid(entity.getSid());

        // Then
        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "supplier", "receivedBy", "dateReceived")
                .isEqualTo(entity);

        assertThat(result.getDateReceived()).isEqualTo(entity.getDate_received());
        assertThat(result.getReceivedBy()).isEqualTo(entity.getReceived_by());

        assertThat(result.getSupplier())
                .usingRecursiveComparison()
                .isEqualTo(entity.getSupplied_by());
    }

    @Test
    void findBySid_whenProductDoesNotExist_throwsProductNotFoundException() {

        // When
        final ThrowableAssert.ThrowingCallable update = () -> subject.findBySid(UUID.randomUUID());

        // Then
        assertThatThrownBy(update).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void findAll_whenProductsExist_returnsProductsList() {

        // Given
        final Product product1 = faker.domain.product().build();
        final Product product2 = faker.domain.product().build();

        final ProductEntity entity1 = faker.entity.product().sid(product1.getSid()).build();
        final ProductEntity entity2 = faker.entity.product().sid(product2.getSid()).build();

        integrationTestPersister.save(entity1);
        integrationTestPersister.save(entity2);

        // When
        final List<Product> result = subject.findAll();

        // Then
        assertThat(result)
                .extracting(Product::getSid)
                .containsExactlyInAnyOrder(product1.getSid(), product2.getSid());
    }

    @Test
    void update_whenProductExists_returnPersistedWarehouse() {

        // Given
        final ProductEntity productEntity = faker.entity.product().build();
        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final SupplierEntity supplierEntity = integrationTestPersister.save(faker.entity.supplier().build());
        final Product product = buildProduct(productEntity, categoryEntity, brandEntity, supplierEntity);

        final Product persistedProduct = subject.create(product);

        final Optional<ProductEntity> createdProduct = productRepository.findBySid(persistedProduct.getSid());

        assertThat(createdProduct).isPresent();

        assertThat(persistedProduct).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(product);

        final BrandEntity brandEntityToUpdate = integrationTestPersister.save(faker.entity.brand().build());
        final CategoryEntity categoryEntityToUpdate = integrationTestPersister.save(faker.entity.category().build());
        final Product productToUpdate = buildProductToUpdate(createdProduct.get(),
                                                             brandEntityToUpdate,
                                                             categoryEntityToUpdate,
                                                             supplierEntity);

        // When
        final Product updatedProduct = subject.update(productToUpdate);

        // Then
        final Optional<ProductEntity> updatedProductEntity = productRepository.findBySid(updatedProduct.getSid());
        assertThat(updatedProductEntity).isPresent();
        assertThat(updatedProduct)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(productToUpdate);
    }

    @Test
    void update_whenProductExists_andCategoryIsInvalid_throwsCategoryNotFoundException() {

        // Given
        final ProductEntity productEntity = faker.entity.product().build();
        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final SupplierEntity supplierEntity = integrationTestPersister.save(faker.entity.supplier().build());
        final Product product = buildProduct(productEntity, categoryEntity, brandEntity, supplierEntity);

        final Product persistedProduct = subject.create(product);

        final Optional<ProductEntity> createdProduct = productRepository.findBySid(persistedProduct.getSid());

        assertThat(createdProduct).isPresent();

        assertThat(persistedProduct).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(product);

        final BrandEntity brandEntityToUpdate = integrationTestPersister.save(faker.entity.brand().build());
        final Product productToUpdateWithInvalidCategory = buildProductWithInvalidCategory(createdProduct.get(),
                                                                                           brandEntityToUpdate,
                                                                                           supplierEntity);

        // When
        final ThrowableAssert.ThrowingCallable updateWithInvalidCategory = () -> subject.update(productToUpdateWithInvalidCategory);

        // Then
        assertThatThrownBy(updateWithInvalidCategory).isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void update_whenProductExists_andBrandIsInvalid_throwsBrandNotFoundException() {

        // Given
        final ProductEntity productEntity = faker.entity.product().build();
        final BrandEntity brandEntity = integrationTestPersister.save(faker.entity.brand().build());
        final CategoryEntity categoryEntity = integrationTestPersister.save(faker.entity.category().build());
        final SupplierEntity supplierEntity = integrationTestPersister.save(faker.entity.supplier().build());
        final Product product = buildProduct(productEntity, categoryEntity, brandEntity, supplierEntity);

        final Product persistedProduct = subject.create(product);

        final Optional<ProductEntity> createdProduct = productRepository.findBySid(persistedProduct.getSid());

        assertThat(createdProduct).isPresent();

        assertThat(persistedProduct).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(product);

        final CategoryEntity categoryEntityToUpdate = integrationTestPersister.save(faker.entity.category().build());
        final Product productToUpdateWithBrandCategory = buildProductWithInvalidBrand(createdProduct.get(),
                                                                                      categoryEntityToUpdate,
                                                                                      supplierEntity);

        // When
        final ThrowableAssert.ThrowingCallable updateWithInvalidBrand = () -> subject.update(productToUpdateWithBrandCategory);

        // Then
        assertThatThrownBy(updateWithInvalidBrand).isInstanceOf(BrandNotFoundException.class);
    }

    @Test
    void update_whenProductNotExists_throwsProductNotFoundException() {

        // Given
        final Product updateInvalidProduct = faker.domain.product().build();

        // When
        final ThrowableAssert.ThrowingCallable updateWithInvalidProduct = () -> subject.update(updateInvalidProduct);

        // Then
        assertThatThrownBy(updateWithInvalidProduct).isInstanceOf(ProductNotFoundException.class);
    }

    private Product buildProductToUpdate(final ProductEntity productEntity,
                                         final BrandEntity brandEntityToUpdate,
                                         final CategoryEntity categoryEntityToUpdate,
                                         final SupplierEntity supplierEntity) {

        return Product.builder()
                .sid(productEntity.getSid())
                .price(faker.number().randomDouble(2, 256, 99999))
                .sku(faker.ancient().hero())
                .description(faker.lorem().characters(100, 1024))
                .discount(faker.number().randomDouble(2, 0, 1))
                .category(Category.builder()
                                  .sid(categoryEntityToUpdate.getSid())
                                  .name(categoryEntityToUpdate.getName())
                                  .build())
                .brand(Brand.builder()
                               .sid(brandEntityToUpdate.getSid())
                               .name(brandEntityToUpdate.getName())
                               .build())
                .warehouseSid(productEntity.getWarehouseSid())
                .receivedBy(productEntity.getReceived_by())
                .dateReceived(productEntity.getDate_received())
                .name(faker.book().title())
                .quantity(faker.random().nextInt(1000))
                .supplier(Supplier.builder()
                                  .sid(supplierEntity.getSid())
                                  .phoneNumber(supplierEntity.getPhoneNumber())
                                  .email(supplierEntity.getEmail())
                                  .leadTime(supplierEntity.getLeadTime())
                                  .name(supplierEntity.getName())
                                  .build())
                .build();
    }

    private Product buildProduct(final ProductEntity productEntity,
                                 final CategoryEntity categoryEntity,
                                 final BrandEntity brandEntity,
                                 final SupplierEntity supplierEntity) {

        return Product.builder()
                .sid(productEntity.getSid())
                .name(productEntity.getName())
                .category(Category.builder()
                                  .name(categoryEntity.getName())
                                  .sid(categoryEntity.getSid())
                                  .build())
                .brand(Brand.builder()
                               .sid(brandEntity.getSid())
                               .name(brandEntity.getName())
                               .build())
                .supplier(Supplier.builder()
                                  .sid(supplierEntity.getSid())
                                  .phoneNumber(supplierEntity.getPhoneNumber())
                                  .email(supplierEntity.getEmail())
                                  .leadTime(supplierEntity.getLeadTime())
                                  .name(supplierEntity.getName())
                                  .build())
                .price(productEntity.getPrice())
                .quantity(productEntity.getQuantity())
                .warehouseSid(productEntity.getWarehouseSid())
                .discount(productEntity.getDiscount())
                .receivedBy(productEntity.getReceived_by())
                .description(productEntity.getDescription())
                .sku(productEntity.getSku())
                .dateReceived(productEntity.getDate_received())
                .build();
    }

    private Product buildProductWithInvalidCategory(final ProductEntity productEntity,
                                                    final BrandEntity brandEntity,
                                                    final SupplierEntity supplierEntity) {

        return Product.builder()
                .sid(productEntity.getSid())
                .name(productEntity.getName())
                .category(Category.builder()
                                  .sid(UUID.randomUUID())
                                  .build())
                .brand(Brand.builder()
                               .sid(brandEntity.getSid())
                               .name(brandEntity.getName())
                               .build())
                .supplier(Supplier.builder()
                                  .sid(supplierEntity.getSid())
                                  .phoneNumber(supplierEntity.getPhoneNumber())
                                  .email(supplierEntity.getEmail())
                                  .leadTime(supplierEntity.getLeadTime())
                                  .name(supplierEntity.getName())
                                  .build())
                .price(productEntity.getPrice())
                .quantity(productEntity.getQuantity())
                .warehouseSid(productEntity.getWarehouseSid())
                .discount(productEntity.getDiscount())
                .receivedBy(productEntity.getReceived_by())
                .description(productEntity.getDescription())
                .sku(productEntity.getSku())
                .dateReceived(productEntity.getDate_received())
                .build();
    }

    private Product buildProductWithInvalidBrand(final ProductEntity productEntity,
                                                 final CategoryEntity categoryEntity,
                                                 final SupplierEntity supplierEntity) {

        return Product.builder()
                .sid(productEntity.getSid())
                .name(productEntity.getName())
                .category(Category.builder()
                                  .name(categoryEntity.getName())
                                  .sid(categoryEntity.getSid())
                                  .build())
                .brand(Brand.builder()
                               .sid(UUID.randomUUID())
                               .build())
                .supplier(Supplier.builder()
                                  .sid(supplierEntity.getSid())
                                  .phoneNumber(supplierEntity.getPhoneNumber())
                                  .email(supplierEntity.getEmail())
                                  .leadTime(supplierEntity.getLeadTime())
                                  .name(supplierEntity.getName())
                                  .build())
                .price(productEntity.getPrice())
                .quantity(productEntity.getQuantity())
                .warehouseSid(productEntity.getWarehouseSid())
                .discount(productEntity.getDiscount())
                .receivedBy(productEntity.getReceived_by())
                .description(productEntity.getDescription())
                .sku(productEntity.getSku())
                .dateReceived(productEntity.getDate_received())
                .build();
    }

    private Product buildProductWithInvalidSupplier(final ProductEntity productEntity,
                                                    final CategoryEntity categoryEntity,
                                                    final BrandEntity brandEntity) {

        return Product.builder()
                .sid(productEntity.getSid())
                .name(productEntity.getName())
                .category(Category.builder()
                                  .name(categoryEntity.getName())
                                  .sid(categoryEntity.getSid())
                                  .build())
                .brand(Brand.builder()
                               .sid(brandEntity.getSid())
                               .name(brandEntity.getName())
                               .build())
                .supplier(Supplier.builder()
                                  .sid(UUID.randomUUID())
                                  .build())
                .price(productEntity.getPrice())
                .quantity(productEntity.getQuantity())
                .warehouseSid(productEntity.getWarehouseSid())
                .discount(productEntity.getDiscount())
                .receivedBy(productEntity.getReceived_by())
                .description(productEntity.getDescription())
                .sku(productEntity.getSku())
                .dateReceived(productEntity.getDate_received())
                .build();
    }
}
