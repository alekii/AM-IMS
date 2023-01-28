package org.am.rest.services.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder(builderClassName = "Builder")
public class ProductUpdateRequest {

    @NotNull
    UUID sid;

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
    @EqualsAndHashCode
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
    @EqualsAndHashCode
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

    Double price;

    @Max(100000)
    int quantity;

    Double discount;

    @NotBlank
    @Size(max = 1024)
    String description;

    String sku;

    public ProductUpdateRequest() {

        super();
    }
}

