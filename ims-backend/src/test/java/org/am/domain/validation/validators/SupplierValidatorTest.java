package org.am.domain.validation.validators;

import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.exceptions.ErrorCode;
import org.am.domain.catalog.exceptions.validations.InvalidEmailException;
import org.am.domain.validation.validators.common.EmailValidator;
import org.am.domain.validation.validators.constants.ValidationErrorConstants;
import org.am.fakers.Faker;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class SupplierValidatorTest {

    private final Faker faker = new Faker();

    @Mock
    private EmailValidator emailValidator;

    @InjectMocks
    private SupplierValidator subject;

    @Test
    void validate_whenEmailIsValid_doesNotThrowException() {

        // Given
        final Supplier supplier = faker.domain.supplier()
                .email("email@email.com")
                .build();

        // When
        Executable executable = () -> subject.validate(supplier);

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    void validate_whenEmailIsInValid_throwsEmailNotValidException() {

        // Given
        final Supplier supplier = faker.domain.supplier()
                .email("email")
                .build();

        doReturn(Optional.of(ValidationErrorConstants.INVALID_EMAIL))
                .when(emailValidator)
                .validate(any());

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable =
                () -> subject.validate(supplier);

        // Then
        Assertions
                .assertThatThrownBy(throwingCallable)
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("%s - Email is invalid - %s", ErrorCode.INVALID_EMAIL_ADDRESS, supplier.getEmail());
    }
}
