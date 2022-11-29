package org.am.persistence.jpa.repositories;

import org.am.infrastructure.purchases.PurchaseRepository;
import org.am.library.entities.PurchaseEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PurchaseRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private PurchaseRepository subject;

    @Test
    void testPurchaseSaveGeneratesId() {

        //Given
        PurchaseEntity purchaseEntity = faker.entity.purchase().build();
        integrationTestPersister.save(purchaseEntity);

        //When
        List<PurchaseEntity> purchases = subject.findAll();

        //Then
        assertThat(purchases).isNotEmpty().hasSize(1);
        assertThat(purchases.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingPurchaseWithNotUniqueSidThrowsException() {

        //Given
        PurchaseEntity purchaseEntity = faker.entity.purchase().build();
        integrationTestPersister.save(purchaseEntity);

        //When
        ThrowableAssert.ThrowingCallable saveWithSameUUID = () -> integrationTestPersister.saveAndFlush(faker.entity.purchase()
                                                                                                                .sid(purchaseEntity.getSid())
                                                                                                                .build());

        //Then
        assertThatThrownBy(saveWithSameUUID)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }

    @Test
    void testSavingPurchaseWithNotUniqueInvoiceNumberThrowsException() {

        //Given
        PurchaseEntity purchaseEntity = faker.entity.purchase().build();
        integrationTestPersister.save(purchaseEntity);

        //When
        ThrowableAssert.ThrowingCallable saveWithSameInvoiceNumber = () -> integrationTestPersister.saveAndFlush(faker.entity.purchase()
                                                                                                                         .invoiceNumber(
                                                                                                                                 purchaseEntity.getInvoiceNumber())
                                                                                                                         .build());

        //Then
        assertThatThrownBy(saveWithSameInvoiceNumber)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }
}
