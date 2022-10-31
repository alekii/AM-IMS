package org.am.domain.api;

import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.NotFound.WarehouseNotFoundException;
import org.am.domain.impl.UpdateWarehouseUseCaseImpl;
import org.am.domain.validation.validators.WarehouseValidator;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdateWarehouseUseCaseImplTest {

    private final Faker faker = new Faker();

    @Mock
    private WarehouseValidator warehouseValidator;

    @Mock
    private WarehouseDAO warehouseDAO;

    @InjectMocks
    private UpdateWarehouseUseCaseImpl subject;

    @Test
    void update_whenWarehouseExists_AndTownIsValid_returnsUpdatedWarehouse() {

        final Warehouse warehouse = faker.domain.warehouse().build();

        doReturn(Optional.of(warehouse))
                .when(warehouseDAO)
                .findBySid(any());

        // When
        subject.update(warehouse);

        //Then
        verify(warehouseValidator).validatePhoneNumber(eq(warehouse));
        verify(warehouseDAO).update(eq(warehouse));
    }

    @Test
    void update_whenWarehouseDoesNotExist_throwsWarehouseNotFoundException() {

        // Given
        final Warehouse warehouse = faker.domain.warehouse().build();

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> subject.update(warehouse);

        //Then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(WarehouseNotFoundException.class);
    }
}
