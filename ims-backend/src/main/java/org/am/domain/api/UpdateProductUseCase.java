package org.am.domain.api;

import org.am.domain.catalog.Product;

public interface UpdateProductUseCase {

    Product update(final Product product);
}
