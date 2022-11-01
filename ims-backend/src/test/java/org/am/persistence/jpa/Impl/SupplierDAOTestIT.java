package org.am.persistence.jpa.Impl;

import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.exceptions.NotFound.SupplierNotFoundException;
import org.am.infrastructure.persistence.impl.SupplierDAOImpl;
import org.am.infrastructure.suppliers.SuppplierRepository;
import org.am.library.entities.SupplierEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SupplierDAOTestIT extends BaseIntegrationTest {

    @Autowired
    private SupplierDAOImpl subject;

    @Autowired
    private SuppplierRepository suppplierRepository;

    @Test
    void findAll_whenSuppliersExist_returnsSuppliers() {

        // Given
        final Supplier supplier1 = faker.domain.supplier().build();
        final Supplier supplier2 = faker.domain.supplier().build();

        final SupplierEntity entity1 = faker.entity.supplier().sid(supplier1.getSid()).build();
        final SupplierEntity entity2 = faker.entity.supplier().sid(supplier2.getSid()).build();

        integrationTestPersister.save(entity1);
        integrationTestPersister.save(entity2);

        // When
        final List<Supplier> result = subject.findAll();

        // Then
        assertThat(result)
                .extracting(Supplier::getSid)
                .containsExactlyInAnyOrder(supplier1.getSid(), supplier2.getSid());
    }

    @Test
    void create_whenSupplierDoesNotExist_returnsPersistedSupplier() {

        // Given
        final SupplierEntity supplierEntity = faker.entity.supplier().build();
        final Supplier supplier = buildSupplier(supplierEntity);

        // When
        final Supplier persistedSupplier = subject.create(supplier);

        // Then
        assertThat(persistedSupplier.getSid()).isEqualTo(supplier.getSid());

        final Optional<SupplierEntity> createdSupplier = suppplierRepository.findBySid(supplierEntity.getSid());
        assertThat(createdSupplier).isPresent();
    }

    @Test
    void findBySid_whenSupplierExists_returnsSupplierEntity() {

        // Given
        final SupplierEntity supplierEntity = faker.entity.supplier().build();

        integrationTestPersister.save(supplierEntity);

        // When
        final Supplier result = subject.findBySid(supplierEntity.getSid());

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(supplierEntity);
    }

    @Test
    void update_whenSupplierExists_updatesValues() {

        // Given
        SupplierEntity supplierEntity = integrationTestPersister.save(faker.entity.supplier().build());

        final Supplier supplier = faker.domain.supplier()
                .sid(supplierEntity.getSid())
                .build();

        // When
        final Supplier updatedSupplier = subject.updateSupplier(supplier);

        // Then
        assertThat(updatedSupplier)
                .usingRecursiveComparison()
                .isEqualTo(supplier);
    }

    @Test
    void update_whenSupplierDoesNotExist_throwsSupplierNotFoundException() {

        // Given
        final Supplier supplier = faker.domain.supplier().build();

        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> subject.updateSupplier(supplier);

        // Then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(SupplierNotFoundException.class);
    }

    private Supplier buildSupplier(SupplierEntity supplierEntity) {

        return Supplier.builder()
                .sid(supplierEntity.getSid())
                .name(supplierEntity.getName())
                .email(supplierEntity.getEmail())
                .phoneNumber(supplierEntity.getPhoneNumber())
                .leadTime(supplierEntity.getLeadTime())
                .build();
    }
}
