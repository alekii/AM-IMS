package org.am.library.events.warehouse;

import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.infrastructure.warehouses.converters.WarehouseToKafkaPayloadConverter;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.events.ImsEventHandler;
import org.am.library.events.resources.WarehouseKafkaPayload;
import org.am.library.publishing.kafka.KafkaImsEventPublisher;
import org.am.library.publishing.kafka.KafkaPublishContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class WarehouseKafkaEventListener extends ImsEventHandler {

    private static final String TOPIC = "topic_warehouses";

    private final WarehouseRepository warehouseRepository;

    private final WarehouseToKafkaPayloadConverter warehouseToKafkaPayloadConverter;

    protected WarehouseKafkaEventListener(KafkaImsEventPublisher kafkaImsEventPublisher, WarehouseRepository warehouseRepository,
                                          WarehouseToKafkaPayloadConverter warehouseToKafkaPayloadConverter) {

        super(kafkaImsEventPublisher);
        this.warehouseRepository = warehouseRepository;
        this.warehouseToKafkaPayloadConverter = warehouseToKafkaPayloadConverter;
    }

    @Async
    @TransactionalEventListener
    public void handleEvent(WarehouseEvent warehouseCreatedEvent) {

        final WarehouseProjection warehouseProjection = warehouseRepository.findByIdFetch(warehouseCreatedEvent.getData());
        final WarehouseKafkaPayload warehouseKafkaPayload = warehouseToKafkaPayloadConverter.convert(warehouseProjection);

        publish(KafkaPublishContext.builder()
                        .imsEvent(warehouseCreatedEvent)
                        .topic(TOPIC)
                        .payload(warehouseKafkaPayload)
                        .build());
    }
}
