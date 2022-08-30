package org.am.rest.controllers;

import org.am.fakers.Faker;
import org.am.rest.services.WarehouseService;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.am.rest.services.responses.WarehouseMinimumResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarehouseController.class)
public class WarehouseControllerTest {

    private final Faker faker = new Faker();

    @MockBean
    private WarehouseService warehouseService;

    @Autowired
    private MockMvc mvc;

    private final Supplier<WarehouseMinimumResponse> sWarehouseMinimumResponse = () -> faker.domain.warehouseMinimumResponse().build();

    private final Supplier<WarehouseFullResponse> sWarehouseFullResponse = () -> faker.domain.warehouseFullResponse().build();

    @Test
    void shouldCreateMockMVC() {

        assertNotNull(mvc);
    }

    @Test
    void getWarehouses_whenWarehousesExist_returns200() throws Exception {

        // Given
        List<WarehouseMinimumResponse> warehouseMinimumResponses = List.of(sWarehouseMinimumResponse.get());

        doReturn(warehouseMinimumResponses).when(warehouseService).getWarehouses();

        // When
        MockHttpServletRequestBuilder requestBuilder = get("/api/warehouses");

        // Then
        final ResultActions result = mvc.perform(requestBuilder).andDo(print());

        result
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attribute("warehouseMinimumResponseList", warehouseMinimumResponses));
    }

    @Test
    void getBySid_whenWarehouseSidIsValidUUD_returns200() throws Exception {

        // Given
        WarehouseFullResponse warehouseFullResponse = sWarehouseFullResponse.get();

        final UUID warehouseSid = UUID.randomUUID();

        doReturn(warehouseFullResponse).when(warehouseService).findBySid(warehouseSid);

        // When
        MockHttpServletRequestBuilder requestBuilder = get("/api/warehouses/{warehouseSid}", warehouseSid);

        // Then
        final ResultActions result = mvc.perform(requestBuilder).andDo(print());

        result
                .andExpect(status().isOk())
                .andExpect(model().attribute("warehouseFullResponse", warehouseFullResponse));
    }

    @Test
    void getUsers_whenWarehouseSidIsNotValidUuid_returnsBadRequest() throws Exception {

        //When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/warehouses/{warehouseSid}", 1);

        //Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }
}
