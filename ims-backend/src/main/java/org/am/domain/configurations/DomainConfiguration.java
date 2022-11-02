package org.am.domain.configurations;

import org.am.domain.validation.validators.SupplierValidator;
import org.am.domain.validation.validators.WarehouseValidator;
import org.am.domain.validation.validators.common.EmailValidator;
import org.am.domain.validation.validators.common.MaxLengthValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    WarehouseValidator warehouseValidator() {

        return new WarehouseValidator();
    }

    @Bean
    SupplierValidator supplierValidator(EmailValidator emailValidator) {

        return new SupplierValidator(emailValidator);
    }

    @Bean
    EmailValidator emailValidator(MaxLengthValidator maxLengthValidator) {

        return new EmailValidator(maxLengthValidator);
    }

    @Bean
    MaxLengthValidator maxLengthValidator() {

        return new MaxLengthValidator();
    }
}
