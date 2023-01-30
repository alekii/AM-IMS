package org.am.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.am.fakers.Faker;
import org.am.rest.services.PurchaseService;
import org.am.rest.services.requests.PurchaseCreateRequest;
import org.am.rest.services.requests.PurchaseUpdateRequest;
import org.am.rest.services.responses.PurchaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

    private final Faker faker = new Faker();

    @MockBean
    private PurchaseService purchaseService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void create_whenCreatingValidPurchase_thenReturnsCreated() throws Exception {

        // Given
        final PurchaseResponse purchaseResponse = faker.domain.purchaseResponse().build();

        final PurchaseCreateRequest request = faker.domain.purchaseCreateRequest().build();

        doReturn(purchaseResponse).when(purchaseService).create(eq(request));

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sid", is(purchaseResponse.getSid().toString())))
                .andExpect(jsonPath("$.invoice", is(purchaseResponse.getInvoice())))
                .andExpect(jsonPath("$.dateReceived", is(purchaseResponse.getDateReceived().toString())))
                .andExpect(jsonPath("$.totalAmount", is(purchaseResponse.getTotalAmount())))
                .andExpect(jsonPath("$.receivedBy", is(purchaseResponse.getReceivedBy())))
                .andExpect(jsonPath("$.supplierResponse.sid", is(purchaseResponse.getSupplierResponse().getSid().toString())))
                .andExpect(jsonPath("$.supplierResponse.name", is(purchaseResponse.getSupplierResponse().getName())))
                .andExpect(jsonPath("$.productResponse", hasSize(1)))
                .andExpect(jsonPath("$.productResponse[0].sid", is(purchaseResponse.getProductResponse().get(0).getSid().toString())))
                .andExpect(jsonPath("$.productResponse[0].name", is(purchaseResponse.getProductResponse().get(0).getName())))
                .andExpect(jsonPath("$.productResponse[0].price", is(purchaseResponse.getProductResponse().get(0).getPrice())));
    }

    @Test
    void create_whenNameIsBlank_returnsBadRequest() throws Exception {

        // Given
        final PurchaseCreateRequest request = faker.domain.purchaseCreateRequest()
                .receivedBy(" ")
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/purchases")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(purchaseService);
    }

    @Test
    void create_whenProductsListIsEmpty_returnsBadRequest() throws Exception {

        // Given
        final PurchaseCreateRequest request = faker.domain.purchaseCreateRequest()
                .products(Collections.emptyList())
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/purchases")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be empty")
                ));

        verifyNoInteractions(purchaseService);
    }

    @Test
    void getBySid_whenPurchaseSidIsValidUuid_thenReturns200() throws Exception {

        // Given
        final UUID purchaseSid = UUID.randomUUID();

        final PurchaseResponse purchaseResponse = faker.domain.purchaseResponse().build();

        doReturn(purchaseResponse).when(purchaseService).getBySid(eq(purchaseSid));

        // When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/purchases/{purchaseSid}", purchaseSid);

        // Then
        mvc.perform(requestBuilder)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sid", is(purchaseResponse.getSid().toString())))
                .andExpect(jsonPath("$.invoice", is(purchaseResponse.getInvoice())))
                .andExpect(jsonPath("$.dateReceived", is(purchaseResponse.getDateReceived().toString())))
                .andExpect(jsonPath("$.totalAmount", is(purchaseResponse.getTotalAmount())))
                .andExpect(jsonPath("$.receivedBy", is(purchaseResponse.getReceivedBy())))
                .andExpect(jsonPath("$.supplierResponse.sid", is(purchaseResponse.getSupplierResponse().getSid().toString())))
                .andExpect(jsonPath("$.supplierResponse.name", is(purchaseResponse.getSupplierResponse().getName())))
                .andExpect(jsonPath("$.productResponse", hasSize(1)))
                .andExpect(jsonPath("$.productResponse[0].sid", is(purchaseResponse.getProductResponse().get(0).getSid().toString())))
                .andExpect(jsonPath("$.productResponse[0].name", is(purchaseResponse.getProductResponse().get(0).getName())))
                .andExpect(jsonPath("$.productResponse[0].price", is(purchaseResponse.getProductResponse().get(0).getPrice())));
    }

    @Test
    void getPurchase_whenPurchaseSidIsInvalidUuid_returns400() throws Exception {

        // Given
        final String invalidSid = "fdfgf-fghs3re";

        // When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/purchases/{purchaseSid}", invalidSid);

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(purchaseService);
    }

    @Test
    void updatePurchase_whenPurchaseRequestIsValidAndPurchaseExists_thenReturns200() throws Exception {

        // Given
        final UUID purchaseSid = UUID.randomUUID();
        final PurchaseResponse purchaseResponse = faker.domain.purchaseResponse().build();
        final PurchaseUpdateRequest request = faker.domain.purchaseUpdateRequest().build();

        doReturn(purchaseResponse).when(purchaseService).update(eq(request), eq(purchaseSid));

        // When
        final MockHttpServletRequestBuilder requestBuilder = patch("/api/purchases/{purchaseSid}", purchaseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sid", is(purchaseResponse.getSid().toString())))
                .andExpect(jsonPath("$.invoice", is(purchaseResponse.getInvoice())))
                .andExpect(jsonPath("$.dateReceived", is(purchaseResponse.getDateReceived().toString())))
                .andExpect(jsonPath("$.totalAmount", is(purchaseResponse.getTotalAmount())))
                .andExpect(jsonPath("$.receivedBy", is(purchaseResponse.getReceivedBy())))
                .andExpect(jsonPath("$.supplierResponse.sid", is(purchaseResponse.getSupplierResponse().getSid().toString())))
                .andExpect(jsonPath("$.supplierResponse.name", is(purchaseResponse.getSupplierResponse().getName())))
                .andExpect(jsonPath("$.productResponse", hasSize(1)))
                .andExpect(jsonPath("$.productResponse[0].sid", is(purchaseResponse.getProductResponse().get(0).getSid().toString())))
                .andExpect(jsonPath("$.productResponse[0].name", is(purchaseResponse.getProductResponse().get(0).getName())))
                .andExpect(jsonPath("$.productResponse[0].price", is(purchaseResponse.getProductResponse().get(0).getPrice())));
    }

    @Test
    void update_whenStatusIsBlank_returnsBadRequest() throws Exception {

        // Given
        final UUID purchaseSid = UUID.randomUUID();
        final PurchaseUpdateRequest request = faker.domain.purchaseUpdateRequest()
                .status(" ")
                .build();

        // When
        final ResultActions result =
                mvc.perform(patch("/api/purchases/{purchaseSid}", purchaseSid)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(purchaseService);
    }

    @Test
    void update_whenStatusIsNotInPurchaseStatusEnum_returnsBadRequest() throws Exception {

        // Given
        final UUID purchaseSid = UUID.randomUUID();
        final PurchaseUpdateRequest request = faker.domain.purchaseUpdateRequest()
                .status("not_in_enum")
                .build();

        // When
        final ResultActions result =
                mvc.perform(patch("/api/purchases/{purchaseSid}", purchaseSid)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("not_in_enum is not valid")
                ));

        verifyNoInteractions(purchaseService);
    }
}
