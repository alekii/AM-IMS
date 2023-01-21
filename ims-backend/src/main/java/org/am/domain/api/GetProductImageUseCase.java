package org.am.domain.api;

import org.am.domain.catalog.ProductImage;

import java.util.List;
import java.util.UUID;

public interface GetProductImageUseCase {

    List<ProductImage> findAllByProductSid(UUID productSid);

    ProductImage findBySid(UUID imageSid);
}
