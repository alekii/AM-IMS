package org.am.infrastructure.Towns;

import org.am.library.entities.TownEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<TownEntity, Integer>, TownQueryDslRepository {

}
