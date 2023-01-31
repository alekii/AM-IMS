package org.am.domain.api;

import org.am.domain.catalog.Purchase;
import org.am.domain.impl.UpdatePurchaseUseCaseImpl;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        doReturn(purchase).when(purchaseDAO).update(eq(purchase));

        // When
        Purchase result = subject.update(purchase);

        // Then
        verify(purchaseDAO).update(eq(purchase));
    }
}
