package org.am.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.am.fakers.Faker;
import org.am.rest.services.ProductService;
import org.am.rest.services.requests.ProductCreateRequest;
import org.am.rest.services.requests.ProductImageCreateRequest;
import org.am.rest.services.requests.ProductUpdateRequest;
import org.am.rest.services.responses.ProductFullResponse;
import org.am.rest.services.responses.ProductImageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private final Faker faker = new Faker();

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void create_whenCreatingValidProduct_thenReturnsCreated() throws Exception {

        // Given
        final ProductFullResponse productFullResponse = faker.domain.productFullResponse().build();

        final ProductCreateRequest request = faker.domain.productCreateRequest().build();

        doReturn(productFullResponse).when(productService).createProduct(any());

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/products");

        // Then
        mvc.perform(requestBuilder.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sid", is(productFullResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(productFullResponse.getName())))
                .andExpect(jsonPath("$.discount", is(productFullResponse.getDiscount())))
                .andExpect(jsonPath("$.brand.sid", is(productFullResponse.getBrand().getSid().toString())))
                .andExpect(jsonPath("$.brand.name", is(productFullResponse.getBrand().getName())))
                .andExpect(jsonPath("$.dateReceived", is(productFullResponse.getDateReceived().toString())))
                .andExpect(jsonPath("$.category.sid", is(productFullResponse.getCategory().getSid().toString())))
                .andExpect(jsonPath("$.category.name", is(productFullResponse.getCategory().getName())))
                .andExpect(jsonPath("$.receivedBy", is(productFullResponse.getReceivedBy())))
                .andExpect(jsonPath("$.description", is(productFullResponse.getDescription())))
                .andExpect(jsonPath("$.price", is(productFullResponse.getPrice())))
                .andExpect(jsonPath("$.quantity", is(productFullResponse.getQuantity())))
                .andExpect(jsonPath("$.supplier.sid", is(productFullResponse.getSupplier().getSid().toString())))
                .andExpect(jsonPath("$.supplier.name", is(productFullResponse.getSupplier().getName())))
                .andExpect(jsonPath("$.warehouseSid", is(productFullResponse.getWarehouseSid().toString())))
                .andExpect(jsonPath("$.sku", is(productFullResponse.getSku())));
    }

    @Test
    void createProduct_whenQuantityExceeds100000_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .quantity(100001)
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        verifyNoInteractions(productService);
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must be less than or equal to 100000")
                ));
    }

    @Test
    void create_whenNameIsBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .name(" ")
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenReceivedByBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .receivedBy(" ")
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductWithBrandNameBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .brand(ProductCreateRequest.BrandRequest.builder()
                               .name(" ")
                               .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be null")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductWithBrandSidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .brand(ProductCreateRequest.BrandRequest.builder()
                               .sid(null)
                               .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be null")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductWithCategorySidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .category(ProductCreateRequest.CategoryRequest.builder()
                                  .sid(null)
                                  .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be null")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductWithCategoryNameBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .category(ProductCreateRequest.CategoryRequest.builder()
                                  .name(null)
                                  .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductWithSupplierSidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .sid(null)
                                  .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be null")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductWithSupplierNameBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .name(" ")
                                  .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductWithSupplierEmailBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .email(" ")
                                  .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductWithSupplierPhoneNumberBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .phoneNumber(" ")
                                  .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void getProduct_whenUuidIsValid_returns200() throws Exception {

        // Given
        final ProductFullResponse productFullResponse = faker.domain.productFullResponse().build();

        final UUID productSid = UUID.randomUUID();

        doReturn(productFullResponse).when(productService).findBySid(productSid);

        // When
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/products/{productSid}", productSid);

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(productFullResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(productFullResponse.getName())))
                .andExpect(jsonPath("$.discount", is(productFullResponse.getDiscount())))
                .andExpect(jsonPath("$.brand.sid", is(productFullResponse.getBrand().getSid().toString())))
                .andExpect(jsonPath("$.brand.name", is(productFullResponse.getBrand().getName())))
                .andExpect(jsonPath("$.dateReceived", is(productFullResponse.getDateReceived().toString())))
                .andExpect(jsonPath("$.category.sid", is(productFullResponse.getCategory().getSid().toString())))
                .andExpect(jsonPath("$.category.name", is(productFullResponse.getCategory().getName())))
                .andExpect(jsonPath("$.receivedBy", is(productFullResponse.getReceivedBy())))
                .andExpect(jsonPath("$.description", is(productFullResponse.getDescription())))
                .andExpect(jsonPath("$.price", is(productFullResponse.getPrice())))
                .andExpect(jsonPath("$.quantity", is(productFullResponse.getQuantity())))
                .andExpect(jsonPath("$.supplier.sid", is(productFullResponse.getSupplier().getSid().toString())))
                .andExpect(jsonPath("$.supplier.name", is(productFullResponse.getSupplier().getName())))
                .andExpect(jsonPath("$.warehouseSid", is(productFullResponse.getWarehouseSid().toString())))
                .andExpect(jsonPath("$.sku", is(productFullResponse.getSku())));
    }

    @Test
    void getProduct_whenProductSidIsNotValidUuid_returnsBadRequest() throws Exception {

        // Given
        final String inValidUuid = "hhdf-sdhsdf";

        // When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/products/{productSid}", inValidUuid);

        //Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(productService);
    }

    @Test
    void updateProduct_whenServiceReturns_returns200() throws Exception {

        // Given
        final ProductFullResponse productFullResponse = faker.domain.productFullResponse().build();
        final ProductUpdateRequest productUpdateRequest = faker.domain.productUpdateRequest().build();

        doReturn(productFullResponse)
                .when(productService)
                .updateProduct(eq(productUpdateRequest));

        // When
        final MockHttpServletRequestBuilder requestBuilder =
                patch("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(productFullResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(productFullResponse.getName())))
                .andExpect(jsonPath("$.discount", is(productFullResponse.getDiscount())))
                .andExpect(jsonPath("$.brand.sid", is(productFullResponse.getBrand().getSid().toString())))
                .andExpect(jsonPath("$.brand.name", is(productFullResponse.getBrand().getName())))
                .andExpect(jsonPath("$.dateReceived", is(productFullResponse.getDateReceived().toString())))
                .andExpect(jsonPath("$.category.sid", is(productFullResponse.getCategory().getSid().toString())))
                .andExpect(jsonPath("$.category.name", is(productFullResponse.getCategory().getName())))
                .andExpect(jsonPath("$.receivedBy", is(productFullResponse.getReceivedBy())))
                .andExpect(jsonPath("$.description", is(productFullResponse.getDescription())))
                .andExpect(jsonPath("$.price", is(productFullResponse.getPrice())))
                .andExpect(jsonPath("$.quantity", is(productFullResponse.getQuantity())))
                .andExpect(jsonPath("$.supplier.sid", is(productFullResponse.getSupplier().getSid().toString())))
                .andExpect(jsonPath("$.supplier.name", is(productFullResponse.getSupplier().getName())))
                .andExpect(jsonPath("$.warehouseSid", is(productFullResponse.getWarehouseSid().toString())))
                .andExpect(jsonPath("$.sku", is(productFullResponse.getSku())));
    }

    @Test
    void updateProduct_whenQuantityExceeds100000_returnsBadRequest() throws Exception {

        // Given
        final ProductUpdateRequest request = faker.domain.productUpdateRequest()
                .quantity(100001)
                .build();

        // When
        final ResultActions result =
                mvc.perform(patch("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        verifyNoInteractions(productService);
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must be less than or equal to 100000")
                ));
    }

    @Test
    void update_whenNameIsBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductUpdateRequest request = faker.domain.productUpdateRequest()
                .name(" ")
                .build();

        // When
        final ResultActions result =
                mvc.perform(patch("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void update_whenUpdatingProductWithBrandNameBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductUpdateRequest request = faker.domain.productUpdateRequest()
                .brand(ProductUpdateRequest.BrandRequest.builder()
                               .name(" ")
                               .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(patch("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be null")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void update_whenUpdatingProductWithBrandSidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductUpdateRequest request = faker.domain.productUpdateRequest()
                .brand(ProductUpdateRequest.BrandRequest.builder()
                               .sid(null)
                               .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(patch("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be null")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void update_whenUpdatingProductWithCategorySidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductUpdateRequest request = faker.domain.productUpdateRequest()
                .category(ProductUpdateRequest.CategoryRequest.builder()
                                  .sid(null)
                                  .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(patch("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be null")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void update_whenUpdatingProductWithCategoryNameBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductUpdateRequest request = faker.domain.productUpdateRequest()
                .category(ProductUpdateRequest.CategoryRequest.builder()
                                  .name(null)
                                  .build())
                .build();

        // When
        final ResultActions result =
                mvc.perform(patch("/api/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductImage_thenReturnsCreated() throws Exception {

        // Given
        final ProductImageResponse productImageResponse = faker.domain.productImageResponse().build();

        final ProductImageCreateRequest request = faker.domain.productImageCreateRequest().build();

        doReturn(productImageResponse).when(productService).addProductImage(eq(request));

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/products/images")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void create_whenCreatingProductImageWithImagePathBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductImageCreateRequest request = faker.domain.productImageCreateRequest()
                .imagePath(" ")
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products/images")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void create_whenCreatingProductImageWithProductSidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductImageCreateRequest request = faker.domain.productImageCreateRequest()
                .productSid(null)
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/products/images")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be null")
                ));

        verifyNoInteractions(productService);
    }

    @Test
    void getProductImage_whenProductImageSidIsValid_returns200() throws Exception {

        // Given
        final ProductImageResponse productImageResponse = faker.domain.productImageResponse().build();

        final UUID imageSid = UUID.randomUUID();

        doReturn(productImageResponse).when(productService).findByImageSid(imageSid);

        // When
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/products/images/{imageSid}", imageSid);

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(productImageResponse.getSid().toString())))
                .andExpect(jsonPath("$.imagePath", is(productImageResponse.getImagePath())));
    }

    @Test
    void getProductImage_whenProductImageSidIsNotValidUuid_returnsBadRequest() throws Exception {

        // Given
        final String inValidUuid = "hhdf-sdhsdf";

        // When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/products/images/{imagSid}", inValidUuid);

        // Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(productService);
    }

    @Test
    void getImagesForProduct_whenUuidIsValid_returns200() throws Exception {

        // Given
        final ProductImageResponse productImageResponse = faker.domain.productImageResponse().build();

        final UUID productSid = UUID.randomUUID();

        doReturn(List.of(productImageResponse)).when(productService).getProductImages(productSid);

        // When
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/products/{productSid}/images", productSid);

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].sid", is(productImageResponse.getSid().toString())))
                .andExpect(jsonPath("$[0].imagePath", is(productImageResponse.getImagePath())));
    }

    @Test
    void getProductImages_whenProductSidIsNotValidUuid_returnsBadRequest() throws Exception {

        // Given
        final String inValidUuid = "hhdf-sdhsdf";

        // When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/products/images/{imageSid}", inValidUuid);

        // Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(productService);
    }

    @Test
    void deleteProductImage_whenImageIdIsValidInteger_thenReturnsOk() throws Exception {

        // Given
        final int imageId = 1;

        doNothing().when(productService).deleteProductImage(eq(imageId));

        // When
        final ResultActions result = mvc.perform(
                delete("/api/products/images/{imageId}", imageId));

        // Then
        result.andExpect(status().isOk());
    }

    @Test
    void deleteProductImage_whenImageIdIsInValidInteger_thenReturnsOk() throws Exception {

        // Given
        final double imageId = Math.random();

        // When
        final ResultActions result = mvc.perform(
                delete("/api/products/images/{imageId}", imageId));

        // Then
        verifyNoInteractions(productService);
        result.andExpect(status().isBadRequest());
    }
}
