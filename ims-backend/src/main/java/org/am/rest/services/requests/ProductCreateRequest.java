package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.am.domain.validation.validators.constants.ValidationConstants;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

import static org.am.domain.validation.validators.constants.ValidationConstants.EMAIL_MAX_LENGTH;

@AllArgsConstructor
@Getter
@Builder(builderClassName = "Builder")
public class ProductCreateRequest {

    @NotBlank
    @Size(max = 50)
    String name;

    @NotNull
    @Valid
    CategoryRequest category;

    @NotNull
    @Valid
    BrandRequest brand;

    @AllArgsConstructor
    @Getter
    @lombok.Builder(builderClassName = "Builder")
    public static class CategoryRequest {

        @NotBlank
        String name;

        @NotNull
        UUID sid;

        public CategoryRequest() {

            super();
        }
    }

    @AllArgsConstructor
    @Getter
    @lombok.Builder(builderClassName = "Builder")
    public static class BrandRequest {

        @NotBlank
        String name;

        @NotNull
        UUID sid;

        public BrandRequest() {

            super();
        }
    }

    @NotBlank
    @Size(max = 50)
    String receivedBy;

    Instant dateReceived;

    Double price;

    @Max(10000)
    int quantity;

    Double discount;

    @NotBlank
    @Size(max = 1024)
    String description;

    @NotNull
    UUID sid;

    UUID warehouseSid;

    @NotNull
    @Valid
    SupplierRequest supplier;

    @AllArgsConstructor
    @Getter
    @lombok.Builder(builderClassName = "Builder")
    public static class SupplierRequest {

        @NotNull
        UUID sid;

        @Size(max = 85)
        String name;

        @NotBlank
        @Pattern(regexp = ValidationConstants.EMAIL_ADDRESS_REGEX, message = ValidationConstants.INVALID_EMAIL)
        @Size(max = EMAIL_MAX_LENGTH)
        String email;

        @NotBlank
        String phoneNumber;

        @Max(30)
        int leadTime;

        public SupplierRequest() {

            super();
        }
    }

    String sku;

    public ProductCreateRequest() {

        super();
    }
}
