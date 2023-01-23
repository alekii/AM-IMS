package org.am.rest.services.requests.converters;

import org.am.commons.utils.Converter;
import org.am.domain.catalog.Brand;
import org.am.domain.catalog.Category;
import org.am.domain.catalog.Product;
import org.am.domain.catalog.Supplier;
import org.am.rest.services.requests.ProductCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductFromProductCreateRequestConverter implements Converter<ProductCreateRequest, Product> {

    @Override
    public Product convert(ProductCreateRequest source) {

        return Product.builder()
                .sid(source.getSid())
                .discount(source.getDiscount())
                .price(source.getPrice())
                .brand(Brand.builder()
                               .sid(source.getBrand().getSid())
                               .name(source.getBrand().getName())
                               .build())
                .category(Category.builder()
                                  .sid(source.getCategory().getSid())
                                  .name(source.getCategory().getName())
                                  .build())
                .dateReceived(source.getDateReceived())
                .receivedBy(source.getReceivedBy())
                .description(source.getDescription())
                .name(source.getName())
                .warehouseSid(source.getWarehouseSid())
                .quantity(source.getQuantity())
                .sku(source.getSku())
                .supplier(Supplier.builder()
                                  .sid(source.getSupplier().getSid())
                                  .email(source.getSupplier().getEmail())
                                  .name(source.getSupplier().getName())
                                  .leadTime(source.getSupplier().getLeadTime())
                                  .phoneNumber(source.getSupplier().getPhoneNumber())
                                  .build())
                .build();
    }
}
