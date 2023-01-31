package org.am.domain.api;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.impl.CreatePurchaseUseCaseImpl;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.api.SupplierDAO;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreatePurchaseUseCaseImplTest {

    private final Faker faker = new Faker();

    @Mock
    private SupplierDAO supplierDAO;

    @Mock
    private PurchaseDAO purchaseDAO;

    @Mock
    private PurchaseToPurchaseEntityConverter purchaseEntityConverter;

    @InjectMocks
    private CreatePurchaseUseCaseImpl subject;

    @Test
    void createPurchase_createsPurchase() {

        // Given
        final Purchase purchase = faker.domain.purchase().build();

        final List<Product> products = purchase.getProducts();

        doReturn(purchase).when(purchaseDAO).create(eq(purchase));

        // When
        subject.create(purchase);

        // Then
        verify(purchaseDAO).create(eq(purchase));
    }
}
