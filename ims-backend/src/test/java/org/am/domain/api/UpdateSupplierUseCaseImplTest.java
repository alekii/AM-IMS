package org.am.domain.api;

import org.am.domain.catalog.Supplier;
import org.am.domain.impl.UpdateSupplierUseCaseImpl;
import org.am.domain.validation.validators.SupplierValidator;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.SupplierDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdateSupplierUseCaseImplTest {

    private final Faker faker = new Faker();

    @Mock
    private SupplierDAO supplierDAO;

    @Mock
    private SupplierValidator supplierValidator;

    @InjectMocks
    private UpdateSupplierUseCaseImpl subject;

    @Test
    void updateSupplier_whenSupplierExists_returnUpdatedSupplier() {

        // Given
        final Supplier supplier = faker.domain.supplier().build();

        doReturn(supplier)
                .when(supplierDAO)
                .updateSupplier(any());

        // When
        subject.update(supplier);

        // Then
        verify(supplierValidator).validate(eq(supplier));
        verify(supplierDAO).updateSupplier(eq(supplier));
    }
}
