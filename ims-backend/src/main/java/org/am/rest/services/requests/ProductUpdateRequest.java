package org.am.rest.services.requests;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Value
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

    @Value
    public static class CategoryRequest {

        @NotBlank
        String name;

        @NotNull
        UUID sid;
    }

    @Value
    public static class BrandRequest {

        @NotBlank
        String name;

        @NotNull
        UUID sid;
    }

    Double price;

    @Max(10000)
    int quantity;

    Double discount;

    @NotBlank
    @Size(max = 1024)
    String description;

    String sku;
}

