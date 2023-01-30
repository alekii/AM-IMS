package org.am.domain.validation.annotations;

import org.am.domain.validation.validators.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumConstraint {

    Class<? extends Enum<?>> value();

    String message() default "${validatedValue} is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

