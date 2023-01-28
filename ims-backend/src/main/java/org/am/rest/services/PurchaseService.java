package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.api.GetPurchaseUseCase;
import org.am.domain.api.UpdatePurchaseUseCase;
import org.am.domain.catalog.Purchase;
import org.am.rest.services.responses.PurchaseResponse;
import org.am.rest.services.responses.converters.PurchaseResponseConverter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final CreatePurchaseUseCase createPurchaseUseCase;

    private final GetPurchaseUseCase getPurchaseUseCase;

    private final UpdatePurchaseUseCase updatePurchaseUseCase;

    private final PurchaseResponseConverter purchaseResponseConverter;

    public PurchaseResponse create(final Purchase purchase) {

        final Purchase createdPurchase = createPurchaseUseCase.create(purchase);

        return purchaseResponseConverter.convert(createdPurchase);
    }

    public PurchaseResponse update(final Purchase purchase) {

        final Purchase updatedPurchase = updatePurchaseUseCase.update(purchase);
        
        return purchaseResponseConverter.convert(updatedPurchase);
    }

    public PurchaseResponse getBySid(final UUID purchaseSid) {

        Purchase purchase = getPurchaseUseCase.findBySid(purchaseSid);

        return purchaseResponseConverter.convert(purchase);
    }
}
