package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Product;

import java.util.List;
import java.util.UUID;

public interface ProductDAO {

    Product create(final Product product);

    List<Product> findAll();

    Product findBySid(final UUID productSid);

    Product update(final Product product);
}
