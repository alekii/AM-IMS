package org.am.domain.api;

import org.am.domain.catalog.Product;

import java.util.List;
import java.util.UUID;

public interface GetProductUseCase {

    List<Product> getProducts();

    Product getBySid(UUID productSid);
}
