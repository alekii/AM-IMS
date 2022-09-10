package org.am.infrastructure.Address;

import org.am.library.entities.AddressEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepositoryImpl extends QuerydslRepositorySupport implements AddressQueryDslRepository {

    public AddressRepositoryImpl() {

        super(AddressEntity.class);
    }
}
