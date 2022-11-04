package org.am.rest.services;

import org.am.domain.api.CreateSupplierUseCase;
import org.am.domain.api.GetSupplierUseCase;
import org.am.domain.api.UpdateSupplierUseCase;
import org.am.domain.catalog.Supplier;
import org.am.fakers.Faker;
import org.am.rest.services.requests.SupplierCreateRequest;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.am.rest.services.requests.converters.SupplierFromSupplierCreateRequest;
import org.am.rest.services.requests.converters.SupplierFromSupplierUpdateRequest;
import org.am.rest.services.responses.SupplierResponse;
import org.am.rest.services.responses.converters.SupplierResponseConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    private final Faker faker = new Faker();

    private final java.util.function.Supplier<Supplier> warehouseSupplier = () -> faker.domain.supplier().build();

    private final java.util.function.Supplier<SupplierResponse> warehouseSupplierResponse = () -> faker.domain.supplierResponse().build();

    @Mock
    private CreateSupplierUseCase createSupplierUseCase;

    @Mock
    private UpdateSupplierUseCase updateSupplierUseCase;

    @Mock
    private GetSupplierUseCase getSupplierUseCase;

    @Mock
    private SupplierResponseConverter supplierResponseConverter;

    @Mock
    private SupplierFromSupplierCreateRequest supplierFromSupplierCreateRequest;

    @Mock
    private SupplierFromSupplierUpdateRequest supplierFromSupplierUpdateRequest;

    @InjectMocks
    private SupplierService subject;

    @Test
    void createSupplier_whenUseCaseReturnsResult_convertResult() {

        // Given
        final Supplier supplier = faker.domain.supplier().build();
        final SupplierCreateRequest request = faker.domain.supplierCreateRequest().build();
        final SupplierResponse supplierResponse = warehouseSupplierResponse.get();

        doReturn(supplier).when(supplierFromSupplierCreateRequest).convert(eq(request));
        doReturn(supplier).when(createSupplierUseCase).create(eq(supplier));
        doReturn(supplierResponse).when(supplierResponseConverter).convert(eq(supplier));

        // When
        final SupplierResponse result = subject.create(request);

        // Then

        final InOrder inOrder = inOrder(supplierFromSupplierCreateRequest,
                                        createSupplierUseCase,
                                        supplierResponseConverter);

        inOrder.verify(supplierFromSupplierCreateRequest).convert(eq(request));
        inOrder.verify(createSupplierUseCase).create(eq(supplier));
        inOrder.verify(supplierResponseConverter).convert(eq(supplier));
        assertThat(result).isNotNull().isEqualTo(supplierResponse);
    }

    @Test
    void getSuppliers_whenSupplierListIsEmpty_returnListSuccessfully() {

        // Given
        final List<Supplier> suppliers = Collections.emptyList();

        when(getSupplierUseCase.getSuppliers()).thenReturn(suppliers);

        // When
        final List<SupplierResponse> result = subject.findAll();

        // Then
        Assertions.assertTrue(result.isEmpty());
        verify(getSupplierUseCase).getSuppliers();
    }

    @Test
    void getWarehouses_whenWarehouseListNotEmpty_returnListSuccessfully() {

        // Given
        final List<Supplier> suppliers = List.of(warehouseSupplier.get(), warehouseSupplier.get());

        final SupplierResponse supplierResponse1 = warehouseSupplierResponse.get();
        final SupplierResponse supplierResponse2 = warehouseSupplierResponse.get();

        when(getSupplierUseCase.getSuppliers()).thenReturn(suppliers);

        doReturn(supplierResponse1, supplierResponse2)
                .when(supplierResponseConverter)
                .convert(any(Supplier.class));

        // When
        final List<SupplierResponse> result = subject.findAll();

        // Then
        assertThat(result).containsExactlyInAnyOrder(supplierResponse1, supplierResponse2);
    }

    @Test
    void findBySid_whenSupplierExists_returnsSupplierSuccessfully() {

        // Given
        final Supplier supplier = warehouseSupplier.get();
        final SupplierResponse supplierResponse = warehouseSupplierResponse.get();

        doReturn(supplier)
                .when(getSupplierUseCase)
                .getSupplierBySid(any(UUID.class));

        doReturn(supplierResponse)
                .when(supplierResponseConverter)
                .convert(any(Supplier.class));

        // When
        final SupplierResponse result = subject.findBySid(supplier.getSid());

        // Then
        assertThat(result.getSid()).isEqualTo(supplierResponse.getSid());
    }

    @Test
    void updateWarehouse_whenUseCaseReturnsResult_convertResult() {

        // Given
        final UUID supplierSid = UUID.randomUUID();
        final SupplierUpdateRequest request = faker.domain.supplierUpdateRequest().build();
        final Supplier supplier = faker.domain.supplier().build();
        final SupplierResponse supplierResponse = warehouseSupplierResponse.get();

        doReturn(supplier).when(supplierFromSupplierUpdateRequest).convert(eq(request), eq(supplierSid));
        doReturn(supplier).when(updateSupplierUseCase).update(eq(supplier));
        doReturn(supplierResponse).when(supplierResponseConverter).convert(eq(supplier));

        // When
        final SupplierResponse result = subject.update(request, supplierSid);

        // Then
        assertThat(result).isEqualTo(supplierResponse);
    }
}
