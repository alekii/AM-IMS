package org.am.rest.services.requests.converters;

import com.github.javafaker.Faker;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplierFromSupplierUpdateRequestTest {

    private final org.am.fakers.Faker domainFaker = new org.am.fakers.Faker();

    private final Faker faker = new Faker();

    private final SupplierFromSupplierUpdateRequest subject = new SupplierFromSupplierUpdateRequestImpl();

    @Test
    void convert_returnsSupplier_FromSupplierUpdateRequest() {

        // Given
        final SupplierUpdateRequest supplierupdateRequest = domainFaker.domain.supplierUpdateRequest().build();
        final Supplier supplier = buildSupplier(supplierupdateRequest);

        // When
        final Supplier updatedSupplier = subject.convert(supplierupdateRequest);

        // Then
        assertThat(updatedSupplier).usingRecursiveComparison().isEqualTo(supplier);
    }

    private Supplier buildSupplier(SupplierUpdateRequest supplierupdateRequest) {

        return Supplier.builder()
                .sid(supplierupdateRequest.getSid())
                .name(supplierupdateRequest.getName())
                .email(supplierupdateRequest.getEmail())
                .phoneNumber(supplierupdateRequest.getPhoneNumber())
                .leadTime(supplierupdateRequest.getLeadTime())
                .build();
    }
}
