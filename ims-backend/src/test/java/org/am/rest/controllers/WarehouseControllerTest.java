package org.am.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.am.fakers.Faker;
import org.am.rest.services.WarehouseService;
import org.am.rest.services.requests.WarehouseAddressCreationRequest;
import org.am.rest.services.requests.WarehouseCreateRequest;
import org.am.rest.services.requests.WarehouseUpdateRequest;
import org.am.rest.services.responses.WarehouseFullResponse;
import org.am.rest.services.responses.WarehouseMinimumResponse;
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

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarehouseController.class)
public class WarehouseControllerTest {

    private final Faker faker = new Faker();

    @MockBean
    private WarehouseService warehouseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private final Supplier<WarehouseMinimumResponse> sWarehouseMinimumResponse = () -> faker.domain.warehouseMinimumResponse().build();

    private final Supplier<WarehouseFullResponse> sWarehouseFullResponse = () -> faker.domain.warehouseFullResponse().build();

    @Test
    void shouldCreateMockMVC() {

        assertNotNull(mvc);
    }

    @Test
    void createWarehouse_whenServiceReturns_returns201() throws Exception {

        // Given
        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest().build();
        final WarehouseMinimumResponse warehouseMinimumResponse = faker.domain.warehouseMinimumResponse().build();

        doReturn(warehouseMinimumResponse).when(warehouseService).create(any(WarehouseCreateRequest.class));

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(warehouseMinimumResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(warehouseMinimumResponse.getName())))
                .andExpect(jsonPath("$.address.street", is(warehouseMinimumResponse.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.town", is(warehouseMinimumResponse.getAddress().getTown())))
                .andExpect(jsonPath("$.address.county", is(warehouseMinimumResponse.getAddress().getCounty())));
    }

    @Test
    void createWarehouse_whenWarehouseNameIsBlank_returns400() throws Exception {

        // Given
        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest()
                .warehouseName(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }

    @Test
    void createWarehouse_whenWarehousePhoneNumberIsBlank_returns400() throws Exception {

        // Given
        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest()
                .phoneNumber(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }

    @Test
    void createWarehouse_whenContactNameIsBlank_returns400() throws Exception {

        // Given
        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest()
                .contactName(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }

    @Test
    void createWarehouse_whenWarehouseAddressIsNull_returns400() throws Exception {

        // Given
        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest()
                .address(null)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }

    @Test
    void createWarehouse_whenWarehouseAddressStreetNameIsEmpty_returns400() throws Exception {

        // Given
        final WarehouseAddressCreationRequest address = faker.domain.warehouseAddressCreationrequest()
                .street("")
                .build();

        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest()
                .address(address)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }

    @Test
    void createWarehouse_whenWarehouseTownIsNull_returns400() throws Exception {

        // Given
        final WarehouseAddressCreationRequest address = faker.domain.warehouseAddressCreationrequest()
                .town(null)
                .build();

        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest()
                .address(address)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }

    @Test
    void createWarehouse_whenWarehouseAddressTownSidIsNull_returns400() throws Exception {

        // Given
        final WarehouseAddressCreationRequest address = faker.domain.warehouseAddressCreationrequest()
                .town(WarehouseAddressCreationRequest.TownRequest.builder().sid(null).build())
                .build();

        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest()
                .address(address)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }

    @Test
    void createWarehouse_whenWarehouseAddressCountySidIsNull_returns400() throws Exception {

        // Given
        final WarehouseAddressCreationRequest address = faker.domain.warehouseAddressCreationrequest()
                .county(WarehouseAddressCreationRequest.CountyRequest.builder().sid(null).build())
                .build();

        final WarehouseCreateRequest warehouseCreateRequest = faker.domain.warehouseCreateRequest()
                .address(address)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/warehouses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseCreateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }

    @Test
    void getWarehouses_whenWarehousesExist_returns200() throws Exception {

        // Given
        List<WarehouseMinimumResponse> warehouseMinimumResponses = List.of(sWarehouseMinimumResponse.get());

        doReturn(warehouseMinimumResponses).when(warehouseService).getWarehouses();

        // When
        MockHttpServletRequestBuilder requestBuilder = get("/api/warehouses");

        // Then
        final ResultActions result = mvc.perform(requestBuilder);

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
        final ResultActions result = mvc.perform(requestBuilder);

        result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(warehouseFullResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(warehouseFullResponse.getName())))
                .andExpect(jsonPath("$.phoneNumber", is(warehouseFullResponse.getPhoneNumber())))
                .andExpect(jsonPath("$.contactName", is(warehouseFullResponse.getContactName())))
                .andExpect(jsonPath("$.address.street", is(warehouseFullResponse.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.street", is(warehouseFullResponse.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.mapUrl", is(warehouseFullResponse.getAddress().getMapUrl())))
                .andExpect(jsonPath("$.address.longitude", is(warehouseFullResponse.getAddress().getLongitude())))
                .andExpect(jsonPath("$.address.latitude", is(warehouseFullResponse.getAddress().getLatitude())))
                .andExpect(jsonPath("$.address.town.sid", is(warehouseFullResponse.getAddress().getTown().getSid().toString())))
                .andExpect(jsonPath("$.address.town.name", is(warehouseFullResponse.getAddress().getTown().getName())))
                .andExpect(jsonPath("$.address.county.sid", is(warehouseFullResponse.getAddress().getCounty().getSid().toString())))
                .andExpect(jsonPath("$.address.county.name", is(warehouseFullResponse.getAddress().getCounty().getName())));
    }

    @Test
    void getUsers_whenWarehouseSidIsNotValidUuid_returnsBadRequest() throws Exception {

        //When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/warehouses/{warehouseSid}", 1);

        //Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }

    @Test
    void updateWarehouse_whenServiceReturns_returns200() throws Exception {

        // Given
        final UUID warehouseSid = UUID.randomUUID();
        final WarehouseUpdateRequest.WarehouseAddressUpdateRequest newAddress = faker.domain.warehouseAddressUpdateRequest().build();
        final WarehouseUpdateRequest warehouseUpdateRequest = faker.domain.warehouseUpdateRequest().build();
        final WarehouseFullResponse warehouseFullResponse = faker.domain.warehouseFullResponse()
                .sid(warehouseSid)
                .build();

        doReturn(warehouseFullResponse).when(warehouseService).update(any(WarehouseUpdateRequest.class), eq(warehouseSid));

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", warehouseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(warehouseFullResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(warehouseFullResponse.getName())))
                .andExpect(jsonPath("$.address.street", is(warehouseFullResponse.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.town.sid", is(warehouseFullResponse.getAddress().getTown().getSid().toString())))
                .andExpect(jsonPath("$.address.town.name", is(warehouseFullResponse.getAddress().getTown().getName())))
                .andExpect(jsonPath("$.address.county.sid", is(warehouseFullResponse.getAddress().getCounty().getSid().toString())))
                .andExpect(jsonPath("$.address.county.name", is(warehouseFullResponse.getAddress().getCounty().getName())));
    }

    @Test
    void updateWarehouse_whenWarehouseSidIsNotValidUuid_returnsBadRequest() throws Exception {

        //When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", 1);

        //Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(warehouseService);
    }

    @Test
    void updateWarehouse_whenWarehouseNameIsBlank_returns400() throws Exception {

        // Given
        final UUID warehouseSid = UUID.randomUUID();
        final WarehouseUpdateRequest warehouseUpdateRequest = faker.domain.warehouseUpdateRequest()
                .warehouseName(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", warehouseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }

    @Test
    void updateWarehouse_whenWarehousePhoneNumberIsBlank_returns400() throws Exception {

        // Given
        final UUID warehouseSid = UUID.randomUUID();
        final WarehouseUpdateRequest warehouseUpdateRequest = faker.domain.warehouseUpdateRequest()
                .phoneNumber(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", warehouseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }

    @Test
    void updateWarehouse_whenContactNameIsBlank_returns400() throws Exception {

        // Given
        final UUID warehouseSid = UUID.randomUUID();
        final WarehouseUpdateRequest warehouseUpdateRequest = faker.domain.warehouseUpdateRequest()
                .contactName(" ").build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", warehouseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }

    @Test
    void updateWarehouse_whenWarehouseAddressIsNull_returns400() throws Exception {

        // Given
        final UUID warehouseSid = UUID.randomUUID();
        final WarehouseUpdateRequest warehouseUpdateRequest = faker.domain.warehouseUpdateRequest()
                .address(null)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", warehouseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }

    @Test
    void updateWarehouse_whenWarehouseAddressStreetNameIsEmpty_returns400() throws Exception {

        // Given
        final UUID warehouseSid = UUID.randomUUID();
        final WarehouseUpdateRequest.WarehouseAddressUpdateRequest address = faker.domain.warehouseAddressUpdateRequest()
                .street("")
                .build();

        final WarehouseUpdateRequest warehouseUpdateRequest = faker.domain.warehouseUpdateRequest()
                .address(address)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", warehouseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }

    @Test
    void updateWarehouse_whenWarehouseTownIsNull_returns400() throws Exception {

        // Given
        final UUID warehouseSid = UUID.randomUUID();
        final WarehouseUpdateRequest.WarehouseAddressUpdateRequest address = faker.domain.warehouseAddressUpdateRequest()
                .town(null)
                .build();

        final WarehouseUpdateRequest warehouseUpdateRequest = faker.domain.warehouseUpdateRequest()
                .address(address)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", warehouseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }

    @Test
    void updateWarehouse_whenWarehouseAddressTownSidIsNull_returns400() throws Exception {

        // Given
        final UUID warehouseSid = UUID.randomUUID();
        final WarehouseUpdateRequest.WarehouseAddressUpdateRequest address = faker.domain.warehouseAddressUpdateRequest()
                .town(WarehouseUpdateRequest.WarehouseAddressUpdateRequest.TownRequest.builder().sid(null).build())
                .build();

        final WarehouseUpdateRequest warehouseUpdateRequest = faker.domain.warehouseUpdateRequest()
                .address(address)
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = put("/api/warehouses/{warehouseSid}", warehouseSid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(warehouseUpdateRequest));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
        verifyNoInteractions(warehouseService);
    }
}
