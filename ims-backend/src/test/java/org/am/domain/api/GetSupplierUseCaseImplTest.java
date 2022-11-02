package org.am.domain.api;

import org.am.domain.catalog.Supplier;
import org.am.domain.impl.GetSupplierUseCaseImpl;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.SupplierDAO;
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
public class GetSupplierUseCaseImplTest {

    private final Faker faker = new Faker();

    private final java.util.function.Supplier<Supplier> warehouseSupplier = () -> faker.domain.supplier().build();

    @Mock
    private SupplierDAO supplierDAO;

    @InjectMocks
    private GetSupplierUseCaseImpl subject;

    @Test
    void getWarehouses_SuppliersExist_returnsSuppliersList() {

        // Given
        final List<Supplier> suppliers = List.of(warehouseSupplier.get(), warehouseSupplier.get());

        doReturn(suppliers).when(supplierDAO).findAll();

        // When
        subject.getSuppliers();

        // Then
        verify(supplierDAO).findAll();
    }

    @Test
    void getBySid_whenSupplierExists_returnsSupplier() {

        // Given
        final Supplier supplier = warehouseSupplier.get();
        doReturn(supplier).when(supplierDAO).findBySid(eq(supplier.getSid()));

        // When
        final Supplier result = subject.getSupplierBySid(supplier.getSid());

        //Then
        assertThat(result).isEqualTo(supplier);
    }
}
