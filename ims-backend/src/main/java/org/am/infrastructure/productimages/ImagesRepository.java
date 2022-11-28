package org.am.infrastructure.productimages;

import org.am.library.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<ImageEntity, Integer> {

}
