package org.am.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.am.fakers.Faker;
import org.am.rest.services.SupplierService;
import org.am.rest.services.requests.SupplierCreateRequest;
import org.am.rest.services.requests.SupplierUpdateRequest;
import org.am.rest.services.responses.SupplierResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SupplierController.class)
public class SupplierControllerTest {

    private final Faker faker = new Faker();

    @MockBean
    private SupplierService supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private final Supplier<SupplierResponse> wSupplierResponse = () -> faker.domain.supplierResponse().build();

    @Test
    void createSupplier_whenServiceReturns_returns201() throws Exception {

        // Given
        final SupplierCreateRequest supplierCreateRequest = faker.domain.supplierCreateRequest().build();
        final SupplierResponse supplierResponse = faker.domain.supplierResponse().build();

        doReturn(supplierResponse).when(supplierService).create(any(SupplierCreateRequest.class));

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/suppliers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(supplierResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(supplierResponse.getName())))
                .andExpect(jsonPath("$.email", is(supplierResponse.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(supplierResponse.getPhoneNumber())));
    }

    @Test
    void createSupplier_whenSupplierNameIsBlank_returns400() throws Exception {

        // Given
        final SupplierCreateRequest supplierCreateRequest = faker.domain.supplierCreateRequest()
                .name(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/suppliers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(supplierService);
    }

    @Test
    void createSupplier_whenSupplierPhoneNumberIsBlank_returns400() throws Exception {

        // Given
        final SupplierCreateRequest supplierCreateRequest = faker.domain.supplierCreateRequest()
                .phoneNumber(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/suppliers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(supplierService);
    }

    @Test
    void createSupplier_whenSupplierLeadTimeIsMoreThan30_returns400() throws Exception {

        // Given
        final SupplierCreateRequest warehouseCreateRequest = faker.domain.supplierCreateRequest()
                .leadTime(31).build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/suppliers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(supplierService);
    }

    @Test
    void getSuppliers_whenSuppliersExist_returns200() throws Exception {

        // Given
        List<SupplierResponse> supplierResponse = List.of(wSupplierResponse.get());

        doReturn(supplierResponse).when(supplierService).findAll();

        // When
        MockHttpServletRequestBuilder requestBuilder = get("/api/suppliers/all");

        // Then
        final ResultActions result = mvc.perform(requestBuilder);

        result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sid", is(supplierResponse.get(0).getSid().toString())))
                .andExpect(jsonPath("$[0].name", is(supplierResponse.get(0).getName())))
                .andExpect(jsonPath("$[0].email", is(supplierResponse.get(0).getEmail())))
                .andExpect(jsonPath("$[0].phoneNumber", is(supplierResponse.get(0).getPhoneNumber())));
    }

    @Test
    void getBySid_whenSupplierSidIsValidUUD_returns200() throws Exception {

        // Given
        SupplierResponse supplierResponse = wSupplierResponse.get();

        final UUID supplierSid = supplierResponse.getSid();

        doReturn(supplierResponse).when(supplierService).findBySid(supplierSid);

        // When
        MockHttpServletRequestBuilder requestBuilder = get("/api/suppliers/{supplierSid}", supplierSid);

        // Then
        final ResultActions result = mvc.perform(requestBuilder);

        result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(supplierResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(supplierResponse.getName())))
                .andExpect(jsonPath("$.email", is(supplierResponse.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(supplierResponse.getPhoneNumber())));
    }

    @Test
    void getSuppliers_whenSupplierSidIsNotValidUuid_returnsBadRequest() throws Exception {

        //When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/suppliers/{supplierSid}", 1);

        //Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(supplierService);
    }

    @Test
    void updateSupplier_whenServiceReturns_returns200() throws Exception {

        // Given
        final UUID supplierSid = UUID.randomUUID();
        final SupplierUpdateRequest supplierUpdateRequest = faker.domain.supplierUpdateRequest().build();
        final SupplierResponse supplierResponse = faker.domain.supplierResponse()
                .sid(supplierSid)
                .build();

        doReturn(supplierResponse).when(supplierService).update(any(SupplierUpdateRequest.class), eq(supplierSid));

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/suppliers/{supplierSid}", supplierSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierUpdateRequest));

        // Then
        mvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(supplierResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(supplierResponse.getName())))
                .andExpect(jsonPath("$.email", is(supplierResponse.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(supplierResponse.getPhoneNumber())));
    }

    @Test
    void updateSupplier_whenSupplierNameIsBlank_returns400() throws Exception {

        // Given
        final UUID supplierSid = UUID.randomUUID();
        final SupplierUpdateRequest supplierUpdateRequest = faker.domain.supplierUpdateRequest()
                .name(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/suppliers/{supplierSid}", supplierSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(supplierService);
    }

    @Test
    void updateSupplier_whenSupplierPhoneNumberIsBlank_returns400() throws Exception {

        // Given
        final UUID supplierSid = UUID.randomUUID();
        final SupplierUpdateRequest supplierUpdateRequest = faker.domain.supplierUpdateRequest()
                .phoneNumber(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/suppliers/{supplierSid}", supplierSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(supplierService);
    }

    @Test
    void updateSupplier_whenSupplierLeadTimeIsMoreThan30_returns400() throws Exception {

        // Given
        final UUID supplierSid = UUID.randomUUID();
        final SupplierCreateRequest warehouseCreateRequest = faker.domain.supplierCreateRequest()
                .leadTime(31).build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/suppliers/{supplierSid}", supplierSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(supplierService);
    }

    @Test
    void updateSupplier_whenSupplierSidIsNotValidUuid_returnsBadRequest() throws Exception {

        //When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/suppliers/{supplierSid}", 1);

        //Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(supplierService);
    }
}
