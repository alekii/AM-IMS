package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.api.GetPurchaseUseCase;
import org.am.domain.api.UpdatePurchaseUseCase;
import org.am.domain.catalog.Purchase;
import org.am.rest.services.requests.PurchaseCreateRequest;
import org.am.rest.services.requests.PurchaseUpdateRequest;
import org.am.rest.services.requests.converters.PurchaseFromPurchaseCreateRequestConverter;
import org.am.rest.services.requests.converters.PurchaseFromPurchaseUpdateRequestConverter;
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

    private final PurchaseFromPurchaseCreateRequestConverter purchaseFromPurchaseCreateRequestConverter;

    private final PurchaseFromPurchaseUpdateRequestConverter purchaseFromPurchaseUpdateRequestConverter;

    public PurchaseResponse create(final PurchaseCreateRequest request) {

        Purchase purchase = purchaseFromPurchaseCreateRequestConverter.convert(request);

        final Purchase createdPurchase = createPurchaseUseCase.create(purchase);

        return purchaseResponseConverter.convert(createdPurchase);
    }

    public PurchaseResponse update(final PurchaseUpdateRequest request, UUID purchaseSid) {

        Purchase purchase = purchaseFromPurchaseUpdateRequestConverter.convert(request, purchaseSid);

        final Purchase updatedPurchase = updatePurchaseUseCase.update(purchase);

        return purchaseResponseConverter.convert(updatedPurchase);
    }

    public PurchaseResponse getBySid(final UUID purchaseSid) {

        Purchase purchase = getPurchaseUseCase.findBySid(purchaseSid);

        return purchaseResponseConverter.convert(purchase);
    }
}
