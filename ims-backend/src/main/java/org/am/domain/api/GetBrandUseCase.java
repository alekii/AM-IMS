package org.am.domain.api;

import org.am.domain.catalog.Brand;

import java.util.List;
import java.util.UUID;

public interface GetBrandUseCase {

    List<Brand> getBrands();

    Brand getBySid(UUID brandSid);
}
