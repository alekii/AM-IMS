package org.am.library.events.warehouse;

import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.infrastructure.warehouses.converters.WarehouseToKafkaPayloadConverter;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.events.resources.WarehouseKafkaPayload;
import org.am.library.publishing.kafka.KafkaEventPublisherHandler;
import org.am.library.publishing.kafka.KafkaImsEventPublisher;
import org.am.library.publishing.kafka.KafkaPublishContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

public class WarehouseCreatedKafkaEventListener extends KafkaEventPublisherHandler<WarehouseCreatedEvent> {

    private static final String TOPIC = "topic_warehouses";

    private final WarehouseRepository warehouseRepository;

    private final WarehouseToKafkaPayloadConverter warehouseToKafkaPayloadConverter;

    protected WarehouseCreatedKafkaEventListener(KafkaImsEventPublisher kafkaImsEventPublisher, WarehouseRepository warehouseRepository,
                                                 WarehouseToKafkaPayloadConverter warehouseToKafkaPayloadConverter) {

        super(kafkaImsEventPublisher);
        this.warehouseRepository = warehouseRepository;
        this.warehouseToKafkaPayloadConverter = warehouseToKafkaPayloadConverter;
    }

    @Async
    @Transactional
    @TransactionalEventListener
    public void handleEvent(WarehouseCreatedEvent warehouseCreatedEvent) {

        final WarehouseProjection warehouseProjection = warehouseRepository.findByIdFetch(warehouseCreatedEvent.getData());
        final WarehouseKafkaPayload warehouseKafkaPayload = warehouseToKafkaPayloadConverter.convert(warehouseProjection);

        publish(KafkaPublishContext.builder()
                        .topic(TOPIC)
                        .payload(warehouseKafkaPayload)
                        .build());
    }
}
