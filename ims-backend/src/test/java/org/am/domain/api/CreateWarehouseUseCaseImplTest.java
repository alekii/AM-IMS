package org.am.domain.api;

import org.am.domain.catalog.Warehouse;
import org.am.domain.impl.CreateWarehouseUseCaseImpl;
import org.am.domain.validators.WarehouseValidator;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateWarehouseUseCaseImplTest {

    private final Faker faker = new Faker();

    @Mock
    private WarehouseValidator warehouseValidator;

    @Mock
    private WarehouseDAO warehouseDAO;

    @InjectMocks
    private CreateWarehouseUseCaseImpl subject;

    @Test
    void createWarehouse_whenPhoneNumberIsValid_createsWarehouse() {

        // Given
        final Warehouse warehouse = faker.domain.warehouse().build();

        // When
        subject.create(warehouse);

        // Then
        verify(warehouseValidator).validatePhoneNumber(eq(warehouse));
        verify(warehouseDAO).create(eq(warehouse));
    }
}
