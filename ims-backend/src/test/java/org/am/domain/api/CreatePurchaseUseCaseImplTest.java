package org.am.domain.api;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.domain.impl.CreatePurchaseUseCaseImpl;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.PurchaseDAO;
import org.am.infrastructure.persistence.api.SupplierDAO;
import org.am.infrastructure.persistence.converters.PurchaseToPurchaseEntityConverter;
import org.am.library.entities.PurchaseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        final PurchaseEntity purchaseEntity = faker.entity.purchase().build();
        final Supplier supplier = faker.domain.supplier().build();
        final List<Product> products = purchase.getProducts();

        doReturn(supplier).when(supplierDAO).findBySid(any());
        doReturn(purchaseEntity).when(purchaseEntityConverter).convert(eq(purchase));
        doReturn(purchase).when(purchaseDAO).create(eq(purchaseEntity), eq(products));

        // When
        Purchase result = subject.create(purchase);

        // Then
        verify(purchaseEntityConverter).convert(eq(purchase));
        verify(purchaseDAO).create(eq(purchaseEntity), eq(products));
        assertThat(result).isNotNull().isEqualTo(purchase);
    }
}
