package org.am.persistence.jpa.repositories;

import org.am.infrastructure.Towns.TownRepository;
import org.am.library.entities.TownEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TownRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private TownRepository townRepository;

    @Test
    void testSaveTownGeneratesId() {

        //Given
        TownEntity town = faker.entity.town().build();
        integrationTestPersister.save(town);

        //When
        List<TownEntity> towns = townRepository.findAll();

        //Then
        assertThat(towns).isNotEmpty().hasSize(1);
        assertThat(towns.get(0).getId()).isNotNull();
    }

    @Test
    void testSavingTownWithNotUniqueSidThrowsException() {

        //Given
        TownEntity town = faker.entity.town().build();
        integrationTestPersister.save(town);

        //When
        ThrowableAssert.ThrowingCallable saveWithSameSid = () -> integrationTestPersister.saveAndFlush(faker.entity.town()
                                                                                                               .sid(town.getSid())
                                                                                                               .build());

        //Then
        assertThatThrownBy(saveWithSameSid)
                .isInstanceOf(PersistenceException.class)
                .hasMessageContaining("org.hibernate.exception.ConstraintViolationException");
    }
}
