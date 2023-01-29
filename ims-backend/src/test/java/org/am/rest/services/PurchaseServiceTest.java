package org.am.rest.services;

import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.api.GetPurchaseUseCase;
import org.am.domain.api.UpdatePurchaseUseCase;
import org.am.domain.catalog.Purchase;
import org.am.fakers.Faker;
import org.am.rest.services.requests.PurchaseCreateRequest;
import org.am.rest.services.requests.PurchaseUpdateRequest;
import org.am.rest.services.requests.converters.PurchaseFromPurchaseCreateRequestConverter;
import org.am.rest.services.requests.converters.PurchaseFromPurchaseUpdateRequestConverter;
import org.am.rest.services.responses.PurchaseResponse;
import org.am.rest.services.responses.converters.PurchaseResponseConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    private final Faker faker = new Faker();

    @Mock
    private CreatePurchaseUseCase createPurchaseUseCase;

    @Mock
    private UpdatePurchaseUseCase updatePurchaseUseCase;

    @Mock
    private GetPurchaseUseCase getPurchaseUseCase;

    @Mock
    private PurchaseResponseConverter purchaseResponseConverter;

    @Mock
    private PurchaseFromPurchaseCreateRequestConverter purchaseFromPurchaseCreateRequestConverter;

    @Mock
    private PurchaseFromPurchaseUpdateRequestConverter purchaseFromPurchaseUpdateRequestConverter;

    @InjectMocks
    private PurchaseService subject;

    private final Supplier<Purchase> sPurchase = () -> faker.domain.purchase().build();

    @Test
    void createPurchase_whenUseCaseReturnsResult_convertResult() {

        // Given
        final Purchase purchase = faker.domain.purchase().build();
        final PurchaseResponse purchaseResponse = faker.domain.purchaseResponse().build();
        final PurchaseCreateRequest purchaseCreateRequest = faker.domain.purchaseCreateRequest().build();

        doReturn(purchase)
                .when(purchaseFromPurchaseCreateRequestConverter)
                .convert(any(PurchaseCreateRequest.class));

        doReturn(purchase)
                .when(createPurchaseUseCase)
                .create(eq(purchase));

        doReturn(purchaseResponse)
                .when(purchaseResponseConverter)
                .convert(any(Purchase.class));

        // When
        subject.create(purchaseCreateRequest);

        // Then
        verify(purchaseFromPurchaseCreateRequestConverter).convert(eq(purchaseCreateRequest));
        verify(purchaseResponseConverter).convert(eq(purchase));
        verify(createPurchaseUseCase).create(eq(purchase));
    }

    @Test
    void updatePurchase_whenUseCaseReturnsResult_convertResult() {

        // Given
        final UUID purchaseSid = UUID.randomUUID();
        final Purchase purchase = faker.domain.purchase().build();
        final PurchaseResponse purchaseResponse = faker.domain.purchaseResponse().build();
        final PurchaseUpdateRequest purchaseUpdateRequest = faker.domain.purchaseUpdateRequest().build();

        doReturn(purchase)
                .when(purchaseFromPurchaseUpdateRequestConverter)
                .convert(any(PurchaseUpdateRequest.class), any());

        doReturn(purchase)
                .when(updatePurchaseUseCase)
                .update(eq(purchase));

        doReturn(purchaseResponse)
                .when(purchaseResponseConverter)
                .convert(any(Purchase.class));

        // When
        subject.update(purchaseUpdateRequest, purchaseSid);

        // Then
        verify(purchaseFromPurchaseUpdateRequestConverter).convert(eq(purchaseUpdateRequest), eq(purchaseSid));
        verify(updatePurchaseUseCase).update(eq(purchase));
        verify(purchaseResponseConverter).convert(eq(purchase));
    }

    @Test
    void create_whenPurchaseAlreadyExists_returnsExistingPurchase() {

        // Given
        final Purchase purchase = faker.domain.purchase().build();
        final PurchaseResponse purchaseResponse = faker.domain.purchaseResponse().build();

        doReturn(purchase)
                .when(getPurchaseUseCase)
                .findBySid(any());

        doReturn(purchaseResponse)
                .when(purchaseResponseConverter)
                .convert(eq(purchase));

        // When
        subject.getBySid(UUID.randomUUID());

        // Then
        verify(getPurchaseUseCase).findBySid(any());
        verify(purchaseResponseConverter).convert(eq(purchase));
    }
}
