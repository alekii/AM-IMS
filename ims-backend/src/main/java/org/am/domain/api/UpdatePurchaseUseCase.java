package org.am.domain.api;

import org.am.domain.catalog.Purchase;

public interface UpdatePurchaseUseCase {

    Purchase update(final Purchase purchase);
}
