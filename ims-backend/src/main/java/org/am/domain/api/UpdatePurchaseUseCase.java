package org.am.domain.api;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;

import java.util.List;

public interface UpdatePurchaseUseCase {

    Purchase update(final Purchase purchase, final List<Product> products);
}
