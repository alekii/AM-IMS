package org.am.rest.services.responses.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.rest.services.responses.PurchaseResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseResponseConverter implements Converter<Purchase, PurchaseResponse> {

    @Override
    public PurchaseResponse convert(Purchase source) {

        return PurchaseResponse.builder()
                .sid(source.getSid())
                .dateReceived(source.getDateReceived())
                .invoice(source.getInvoice())
                .supplierResponse(PurchaseResponse.SupplierResponse.builder()
                                          .sid(source.getSupplier().getSid())
                                          .name(source.getSupplier().getName())
                                          .build())
                .productResponse(getProductResponse(source.getProducts()))
                .totalAmount(source.getTotalAmount())
                .build();
    }

    private List<PurchaseResponse.ProductResponse> getProductResponse(List<Product> products) {

        return products.stream()
                .map(product -> PurchaseResponse.ProductResponse.builder()
                        .sid(product.getSid())
                        .name(product.getName())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
