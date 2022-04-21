package org.am.infrastructure.persistence.api;

import org.am.domain.catalog.Brand;

import java.util.List;
import java.util.UUID;

public interface BrandDAO {

    Brand create(final Brand brand);

    List<Brand> findAll();

    Brand findBySid(final UUID brandSid);
}
