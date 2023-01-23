package org.am.rest.services;

import org.am.domain.api.CreateProductImageUseCase;
import org.am.domain.api.CreateProductUseCase;
import org.am.domain.api.DeleteProductImageUseCase;
import org.am.domain.api.GetProductUseCase;
import org.am.domain.api.UpdateProductUseCase;
import org.am.fakers.Faker;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final Faker faker = new Faker();

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private GetProductUseCase getProductUseCase;

    @Mock
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private CreateProductImageUseCase createProductImageUseCase;

    @Mock
    private DeleteProductImageUseCase deleteProductImageUseCase;

    @InjectMocks
    private ProductService subject;
}
