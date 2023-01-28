package org.am.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.am.fakers.Faker;
import org.am.rest.services.CategoryService;
import org.am.rest.services.requests.CategoryCreationRequest;
import org.am.rest.services.responses.CategoryResponse;
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

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    private final Faker faker = new Faker();

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void create_whenCreatingValidCategory_thenReturnsCreated() throws Exception {

        // Given
        final CategoryResponse categoryFullResponse = faker.domain.categoryResponse().build();

        final CategoryCreationRequest request = faker.domain.categoryCreationRequest().build();

        doReturn(categoryFullResponse).when(categoryService).create(request);

        // When
        final MockHttpServletRequestBuilder requestBuilder = post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));

        // Then
        mvc.perform(requestBuilder)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sid", is(categoryFullResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(categoryFullResponse.getName())));
    }

    @Test
    void create_whenNameIsBlank_returnsBadRequest() throws Exception {

        // Given
        final CategoryCreationRequest request = faker.domain.categoryCreationRequest()
                .name(" ")
                .build();

        // When
        final ResultActions result =
                mvc.perform(post("/api/categories")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(request)));

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(errorResponse -> assertTrue(Objects.requireNonNull(errorResponse.getResolvedException())
                                                               .getMessage().contains("must not be blank")
                ));

        verifyNoInteractions(categoryService);
    }

    @Test
    void getCategory_whenUuidIsValid_returns200() throws Exception {

        // Given
        final CategoryResponse categoryResponse = faker.domain.categoryResponse().build();

        final UUID categorySid = UUID.randomUUID();

        doReturn(categoryResponse).when(categoryService).getBySid(categorySid);

        // When
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/categories/{categorySid}", categorySid);

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sid", is(categoryResponse.getSid().toString())))
                .andExpect(jsonPath("$.name", is(categoryResponse.getName())));
    }

    @Test
    void getCategory_whenCategorySidIsNotValidUuid_returnsBadRequest() throws Exception {

        // Given
        final String inValidUuid = "hhdf-sdhsdf";

        // When
        final MockHttpServletRequestBuilder requestBuilder = get("/api/categories/{categorySid}", inValidUuid);

        // Then
        mvc.perform(requestBuilder).andExpect(status().isBadRequest());

        verifyNoInteractions(categoryService);
    }

    @Test
    void getCategorys_returns200() throws Exception {

        // Given
        final CategoryResponse categoryResponse = faker.domain.categoryResponse().build();

        doReturn(List.of(categoryResponse)).when(categoryService).get();

        // When
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/categories");

        // Then
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].sid", is(categoryResponse.getSid().toString())))
                .andExpect(jsonPath("$[0].name", is(categoryResponse.getName())));
    }
}
