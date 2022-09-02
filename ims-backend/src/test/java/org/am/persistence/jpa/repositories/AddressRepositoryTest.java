package org.am.persistence.jpa.repositories;

import org.am.infrastructure.Address.AddressRepository;
import org.am.library.entities.AddressEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void testAddressSaveGeneratesId() {

        //Given
        integrationTestPersister.save(faker.entity.address().build());

        //When
        List<AddressEntity> addresses = addressRepository.findAll();

        //Then
        assertThat(addresses).isNotEmpty().hasSize(1);
        assertThat(addresses.get(0).getId()).isNotNull();
    }
}
