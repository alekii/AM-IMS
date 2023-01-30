package org.am.domain.api;

import org.am.domain.catalog.Purchase;
import org.am.domain.impl.GetPurchaseUseCaseImpl;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.converters.PurchaseEntityToPurchaseConverter;
import org.am.library.entities.PurchaseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GetPurchaseUseCaseImplTest {

    private final Faker faker = new Faker();

    private final Supplier<Purchase> sPurchase = () -> faker.domain.purchase().build();

    private final Supplier<PurchaseEntity> sPurchaseEntity = () -> faker.entity.purchase().build();

    @Mock
    private PurchaseDAO purchaseDAO;

    @Mock
    private PurchaseEntityToPurchaseConverter purchaseEntityToPurchaseConverter;

    @InjectMocks
    private GetPurchaseUseCaseImpl subject;

    @Test
    void getPurchase_whenPurchaseExists_returnsPurchaseSuccessfully() {

        // Given
        final UUID purchaseSid = UUID.randomUUID();
        final Purchase purchase = sPurchase.get();
        final PurchaseEntity purchaseEntity = sPurchaseEntity.get();

        doReturn(purchase).when(purchaseEntityToPurchaseConverter)
                .convert(eq(purchaseEntity));

        doReturn(purchaseEntity).when(purchaseDAO)
                .findBySid(eq(purchaseSid));

        // When
        subject.findBySid(purchaseSid);

        // Then
        verify(purchaseEntityToPurchaseConverter).convert(eq(purchaseEntity));
        verify(purchaseDAO).findBySid(eq(purchaseSid));
    }
}
