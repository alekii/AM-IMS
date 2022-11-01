package org.am.domain.validation.validators;

import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.validations.InvalidPhoneNumberException;
import org.am.fakers.Faker;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class WarehouseValidatorTest {

    private final Faker faker = new Faker();

    private static final WarehouseValidator subject = new WarehouseValidator();

    @Test
    void validate_whenPhoneNumberIsInvalid_doesNotThrowException() {

        // Given
        final Warehouse warehouse = faker.domain.warehouse()
                .phoneNumber("0723456789")
                .build();

        // When
        Executable executable = () -> subject.validatePhoneNumber(warehouse);

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    void validate_whenPhoneNumberIsNull_doesNotThrowException() {

        // Given
        final Warehouse warehouse = faker.domain.warehouse()
                .phoneNumber(null)
                .build();

        // When
        Executable executable = () -> subject.validatePhoneNumber(warehouse);

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    void validate_whenPhoneNumberIsInvalid_throwsException() {

        // Given
        final Warehouse warehouse = faker.domain.warehouse()
                .phoneNumber("-123 2324 43")
                .build();

        // When
        final ThrowableAssert.ThrowingCallable throwingCallable = () -> subject.validatePhoneNumber(warehouse);

        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(InvalidPhoneNumberException.class);
    }
}
