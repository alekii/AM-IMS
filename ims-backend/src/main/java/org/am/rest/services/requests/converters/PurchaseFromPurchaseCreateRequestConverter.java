package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Purchase;
import org.am.domain.catalog.Supplier;
import org.am.library.entities.util.PurchaseStatus;
import org.am.rest.services.requests.PurchaseCreateRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PurchaseFromPurchaseCreateRequestConverter implements Converter<PurchaseCreateRequest, Purchase> {

    @Override
    public Purchase convert(PurchaseCreateRequest source) {

        return Purchase.builder()
                .invoice(source.getInvoice())
                .sid(UUID.randomUUID())
                .status(PurchaseStatus.PENDING_APPROVAL)
                .supplier(convertSupplier(source.getSupplier()))
                .products(convertProducts(source.getProducts()))
                .warehouseSid(source.getWarehouseSid())
                .build();
    }

    private List<Product> convertProducts(List<Integer> products) {

        return products.stream()
                .map(product -> Product.builder()
                        .id(product)
                        .build())
                .collect(Collectors.toList());
    }

    private Supplier convertSupplier(UUID supplier) {

        return Supplier.builder()
                .sid(supplier)
                .build();
    }
}
