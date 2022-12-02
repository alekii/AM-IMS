package org.am.infrastructure.brand;

import org.am.library.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {

    Optional<BrandEntity> findBySid(final UUID sid);

    Optional<BrandEntity> findByName(final String brandName);
}
