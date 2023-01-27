package org.am.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.am.fakers.Faker;
import org.am.rest.services.ProductService;
import org.am.rest.services.requests.ProductCreateRequest;
import org.am.rest.services.responses.ProductFullResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
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
    void createProductWhenQuantityExceeds10000_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .quantity(100001)
                .build();

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
    void create_WhenNameIsBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .name(" ")
                .build();

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
    void create_WhenReceivedByBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .receivedBy(" ")
                .build();

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
    void create_WhenCreatingProductWithBrandNameBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .brand(ProductCreateRequest.BrandRequest.builder()
                               .name(" ")
                               .build())
                .build();

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
    void create_WhenCreatingProductWithBrandSidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .brand(ProductCreateRequest.BrandRequest.builder()
                               .sid(null)
                               .build())
                .build();

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
    void create_WhenCreatingProductWithCategorySidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .category(ProductCreateRequest.CategoryRequest.builder()
                                  .sid(null)
                                  .build())
                .build();

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
    void create_WhenCreatingProductWithCategoryNameBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .category(ProductCreateRequest.CategoryRequest.builder()
                                  .name(null)
                                  .build())
                .build();

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
    void create_WhenCreatingProductWithSupplierSidNull_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .sid(null)
                                  .build())
                .build();

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
    void create_WhenCreatingProductWithSupplierNameBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .name(" ")
                                  .build())
                .build();

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
    void create_WhenCreatingProductWithSupplierEmailBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .email(" ")
                                  .build())
                .build();

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
    void create_WhenCreatingProductWithSupplierPhoneNumberBlank_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .phoneNumber(" ")
                                  .build())
                .build();

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
    void create_WhenCreatingProductWithSupplier_returnsBadRequest() throws Exception {

        // Given
        final ProductCreateRequest request = faker.domain.productCreateRequest()
                .supplier(ProductCreateRequest.SupplierRequest.builder()
                                  .email(" ")
                                  .build())
                .build();

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
}
