package org.am.domain.api;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;

import java.util.List;

public interface CreatePurchaseUseCase {

    Purchase create(Purchase purchase, List<Product> products);
}
