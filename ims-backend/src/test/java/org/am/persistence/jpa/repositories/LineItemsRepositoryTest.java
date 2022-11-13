package org.am.persistence.jpa.repositories;

import org.am.infrastructure.lineItems.LineItemsRepository;
import org.am.library.entities.PurchaseProductEntity;
import org.am.persistence.jpa.configuration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LineItemsRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private LineItemsRepository lineItemsRepository;

    private PurchaseProductEntity lineItem;

    @Test
    void testLineItemSaveGeneratesId() {

        //Given
        PurchaseProductEntity lineItem = faker.entity.lineItems().build();

        //When
        List<PurchaseProductEntity> lineItems = lineItemsRepository.findAll();

        //Then
        assertThat(lineItems).isNotEmpty().hasSize(1);
        assertThat(lineItems.get(0).getId()).isNotNull();
    }
}
