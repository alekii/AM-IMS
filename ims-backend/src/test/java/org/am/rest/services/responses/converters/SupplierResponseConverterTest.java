package org.am.rest.services.responses.converters;

import org.am.domain.catalog.Supplier;
import org.am.fakers.Faker;
import org.am.rest.services.responses.SupplierResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplierResponseConverterTest {

    private final Faker faker = new Faker();

    private final SupplierResponseConverter supplierResponseConverter = SupplierResponseConverter.INSTANCE;

    @Test
    void convert_Supplier_returnsSupplierResponse() {

        // Given
        final Supplier supplier = faker.domain.supplier().build();
        final SupplierResponse supplierResponse = buildSupplierResponse(supplier);

        // When
        final SupplierResponse response = supplierResponseConverter.convert(supplier);

        // Then
        assertThat(supplierResponse).usingRecursiveComparison().isEqualTo(response);
    }

    private SupplierResponse buildSupplierResponse(Supplier supplier) {

        return SupplierResponse.builder()
                .sid(supplier.getSid())
                .name(supplier.getName())
                .phoneNumber(supplier.getPhoneNumber())
                .email(supplier.getEmail())
                .leadTime(supplier.getLeadTime())
                .build();
    }
}
