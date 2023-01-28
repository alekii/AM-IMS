package org.am.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.am.fakers.Faker;
import org.am.rest.services.BrandService;
import org.am.rest.services.requests.BrandCreationRequest;
import org.am.rest.services.responses.BrandResponse;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
public class BrandControllerTest {

    private final Faker faker = new Faker();

    @MockBean
    private BrandService brandService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void create_whenCreatingValidBrand_thenReturnsCreated() throws Exception {

        // Given
        final BrandResponse brandFullResponse = faker.domain.brandResponse().build();

        final BrandCreationRequest request = faker.domain.brandCreationRequest().build();

        doReturn(brandFullResponse).when(brandService).create(request);

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sid", is(brandFullResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(brandFullResponse.getName())));
    }

    @Test
    void create_whenNameIsBlank_returnsBadRequest() throws Exception {

        // Given
        final BrandCreationRequest request = faker.domain.brandCreationRequest()
                .name(" ")
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/brands")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(brandService);
    }

    @Test
    void getBrand_whenUuidIsValid_returns200() throws Exception {

        // Given
        final BrandResponse brandResponse = faker.domain.brandResponse().build();

        final UUID brandSid = UUID.randomUUID();

        doReturn(brandResponse).when(brandService).getBySid(brandSid);

        // When
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/brands/{brandSid}", brandSid);

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(brandResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(brandResponse.getName())));
    }

    @Test
    void getBrand_whenBrandSidIsNotValidUuid_returnsBadRequest() throws Exception {

        // Given
        final String inValidUuid = "hhdf-sdhsdf";

        // When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/brands/{brandSid}", inValidUuid);

        // Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(brandService);
    }

    @Test
    void getBrands_returns200() throws Exception {

        // Given
        final BrandResponse brandResponse = faker.domain.brandResponse().build();

        doReturn(List.of(brandResponse)).when(brandService).get();

        // When
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/brands");

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].sid", is(brandResponse.getSid().toString())))
                .andExpect(jsonPath("$[0].name", is(brandResponse.getName())));
    }
}
