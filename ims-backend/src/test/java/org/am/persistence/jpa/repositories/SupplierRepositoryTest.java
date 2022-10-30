package org.am.persistence.jpa.repositories;

import org.am.infrastructure.suppliers.SuppplierRepository;
import org.am.library.entities.SupplierEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SupplierRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private SuppplierRepository subject;

    @Test
    void testSupplierSaveGeneratesId() {

        //Given
        SupplierEntity supplierEntity = faker.entity.supplier().build();
        integrationTestPersister.save(supplierEntity);

        //When
        List<SupplierEntity> suppliers = subject.findAll();

        //Then
        assertThat(suppliers).isNotEmpty().hasSize(1);
        assertThat(suppliers.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingSupplierWithNotUniqueSidThrowsException() {

        // Given
        SupplierEntity supplier = faker.entity.supplier().build();
        integrationTestPersister.save(supplier);

        // When
        ThrowableAssert.ThrowingCallable supplierwithSameSid = () -> integrationTestPersister.saveAndFlush(faker.entity.supplier()
                                                                                                                   .sid(supplier.getSid())
                                                                                                                   .build());

        // Then
        assertThatThrownBy(supplierwithSameSid)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingSupplierWithNotUniqueNameThrowsException() {

        // Given
        SupplierEntity supplier = faker.entity.supplier().build();
        integrationTestPersister.save(supplier);

        // When
        // synchronizing the persistence context to the database fails(flushing). Cannot flush since name is not unique
        ThrowableAssert.ThrowingCallable supplierwithSameName = () -> integrationTestPersister.saveAndFlush(faker.entity.supplier()
                                                                                                                    .name(supplier.getName())
                                                                                                                    .build());

        // Then
        assertThatThrownBy(supplierwithSameName)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingSupplierWithNotUniquePhoneNumberThrowsException() {

        // Given
        SupplierEntity supplier = faker.entity.supplier().build();
        integrationTestPersister.save(supplier);

        // When
        // synchronizing the persistence context to the database fails(flushing). Cannot flush since name is not unique
        ThrowableAssert.ThrowingCallable supplierwithSamePhoneNumber = () -> integrationTestPersister.saveAndFlush(faker.entity.supplier()
                                                                                                                           .phoneNumber(
                                                                                                                                   supplier.getPhoneNumber())
                                                                                                                           .build());

        // Then
        assertThatThrownBy(supplierwithSamePhoneNumber)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingSupplierWithNotUniqueEmailThrowsException() {

        // Given
        SupplierEntity supplier = faker.entity.supplier().build();
        integrationTestPersister.save(supplier);

        // When
        // synchronizing the persistence context to the database fails(flushing). Cannot flush since name is not unique
        ThrowableAssert.ThrowingCallable supplierwithSameEmail = () -> integrationTestPersister.saveAndFlush(faker.entity.supplier()
                                                                                                                     .email(supplier.getEmail())
                                                                                                                     .build());

        // Then
        assertThatThrownBy(supplierwithSameEmail)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }
}
