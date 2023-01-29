package org.am.domain.api;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.impl.UpdatePurchaseUseCaseImpl;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.am.library.entities.PurchaseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdatePurchaseUseCaseImplTest {

    private final Faker faker = new Faker();

    @Mock
    private PurchaseDAO purchaseDAO;

    @Mock
    private PurchaseToPurchaseEntityConverter purchaseEntityConverter;

    @InjectMocks
    private UpdatePurchaseUseCaseImpl subject;

    @Test
    void updatePurchase_whenPurchaseExist_updatesPurchase() {

        // Given
        final Purchase purchase = faker.domain.purchase().build();
        final PurchaseEntity purchaseEntity = faker.entity.purchase().build();
        final List<Product> products = purchase.getProducts();

        doReturn(purchaseEntity).when(purchaseDAO).findBySid(eq(purchase.getSid()));
        doReturn(purchase).when(purchaseDAO).update(eq(purchaseEntity), eq(products));

        // When
        Purchase result = subject.update(purchase);

        // Then
        verify(purchaseDAO).update(eq(purchaseEntity), eq(products));
        assertThat(result).isNotNull().isEqualTo(purchase);
    }
}
