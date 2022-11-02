package org.am.domain.api;

import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.validations.InvalidEmailException;
import org.am.domain.impl.CreateSupplierUseCaseImpl;
import org.am.domain.validation.validators.SupplierValidator;
import org.am.fakers.Faker;
import org.am.infrastructure.persistence.api.SupplierDAO;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateSupplierUseCaseImplTest {

    private final Faker faker = new Faker();

    private final java.util.function.Supplier<Supplier> warehouseSupplier = () -> faker.domain.supplier().build();

    @Mock
    private SupplierValidator supplierValidator;

    @Mock
    private SupplierDAO supplierDAO;

    @InjectMocks
    private CreateSupplierUseCaseImpl subject;

    @Test
    void createSupplier_whenEmailIsValid_createsSupplier() {

        // Given
        final Supplier supplier = warehouseSupplier.get();

        // When
        subject.create(supplier);

        // Then
        verify(supplierValidator).validate(eq(supplier));
        verify(supplierDAO).create(eq(supplier));
    }

    @Test
    void createSupplier_whenEmailIsInvalid_throwsEmailNotValidException() {

        // Given
        final Supplier supplier = warehouseSupplier.get();

        doThrow(new InvalidEmailException("Email is invalid"))
                .when(supplierValidator)
                .validate(any());

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable =
                () -> subject.create(supplier);

        // Then
        Assertions
                .assertThatThrownBy(throwingCallable)
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("%s - Email is invalid", ErrorCode.INVALID_EMAIL_ADDRESS);
    }
}
