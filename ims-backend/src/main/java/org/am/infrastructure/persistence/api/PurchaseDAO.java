package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.library.entities.PurchaseEntity;

import java.util.List;

public interface PurchaseDAO {

    Purchase create(PurchaseEntity purchase, final List<Product> products);
}
