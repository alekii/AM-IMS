package org.am.rest.services;

import lombok.RequiredArgsConstructor;
import org.am.domain.api.CreatePurchaseUseCase;
import org.am.domain.api.GetPurchaseUseCase;
import org.am.domain.api.UpdateProductUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final CreatePurchaseUseCase createPurchaseUseCase;

    private final GetPurchaseUseCase getPurchaseUseCase;

    private final UpdateProductUseCase updateProductUseCase;

  /*  public PurchaseResponse createPurchase(){
        return createPurchaseUseCase.create()
    }*/
}
