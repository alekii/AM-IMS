package org.am.domain.validation.validators.common;

import org.am.domain.validation.validators.constants.ValidationConstants;
import org.am.domain.validation.validators.constants.ValidationErrorConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailValidatorTest {

    @Mock
    private MaxLengthValidator maxLengthValidator;

    @InjectMocks
    private EmailValidator subject;

    @Test
    void validate_whenEmailIsNull_returnsBlankEmail() {

        // Given
        final String email = null;

        // When
        final Optional<ValidationErrorConstants> result = subject.validate(email);

        // Then
        assertThat(result)
                .hasValue(ValidationErrorConstants.BLANK_EMAIL);
    }

    @Test
    void validate_whenEmailIsBlank_returnsBlankEmail() {

        // Given
        final String email = " ";

        // When
        final Optional<ValidationErrorConstants> result = subject.validate(email);

        // Then
        assertThat(result)
                .hasValue(ValidationErrorConstants.BLANK_EMAIL);
    }

    @Test
    void validate_whenEmailIsMoreThan255Characters_returnsEmailMoreThan255Characters() {

        // Given
        final String email = "email";

        doReturn(false)
                .when(maxLengthValidator)
                .validateMaxLength(any(), anyInt());

        // When
        final Optional<ValidationErrorConstants> result = subject.validate(email);

        // Then
        assertThat(result)
                .hasValue(ValidationErrorConstants.EMAIL_MORE_THAN_255_CHARACTERS);
    }

    @Test
    void validate_whenEmailIsValid_returnsEmpty() {

        // Given
        final String email = "email@email.com";

        doReturn(true)
                .when(maxLengthValidator)
                .validateMaxLength(any(), anyInt());

        // When
        final Optional<ValidationErrorConstants> result = subject.validate(email);

        // Then
        assertThat(result).isEmpty();

        verify(maxLengthValidator).validateMaxLength(eq(email), eq(ValidationConstants.EMAIL_MAX_LENGTH));
    }
}
