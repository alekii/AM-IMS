package org.am.rest.services.requests.converters;

import com.github.javafaker.Faker;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.requests.SupplierCreateRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplierFromSupplierCreateRequestTest {

    private final org.am.fakers.Faker domainFaker = new org.am.fakers.Faker();

    private final Faker faker = new Faker();

    private final SupplierFromSupplierCreateRequest subject = new SupplierFromSupplierCreateRequestImpl();

    @Test
    void convert_returnsSupplier_FromSupplierCreateRequest() {

        // Given
        final SupplierCreateRequest supplierCreateRequest = domainFaker.domain.supplierCreateRequest().build();
        final Supplier supplier = buildSupplier(supplierCreateRequest);

        // When
        final Supplier createdSupplier = subject.convert(supplierCreateRequest);

        // Then
        assertThat(createdSupplier).usingRecursiveComparison().ignoringFields("sid").isEqualTo(supplier);
        assertThat(createdSupplier.getSid()).isNotNull();
    }

    private Supplier buildSupplier(SupplierCreateRequest supplierCreateRequest) {

        return Supplier.builder()
                .name(supplierCreateRequest.getName())
                .email(supplierCreateRequest.getEmail())
                .phoneNumber(supplierCreateRequest.getPhoneNumber())
                .leadTime(supplierCreateRequest.getLeadTime())
                .build();
    }
}
