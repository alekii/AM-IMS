package org.am.infrastructure.productimages;

import org.am.library.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImagesRepository extends JpaRepository<ImageEntity, Integer> {

    List<ImageEntity> findByProductSid(UUID productSid);
}
