package org.am.domain.api;

import org.am.domain.catalog.Product;

public interface CreateProductUseCase {

    Product create(Product product);
}
