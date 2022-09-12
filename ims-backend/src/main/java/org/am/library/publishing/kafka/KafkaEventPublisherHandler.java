package org.am.library.publishing.kafka;

import org.am.library.events.ImsEventHandler;

public class KafkaEventPublisherHandler<ImsEvent> extends ImsEventHandler<ImsEvent, KafkaPublishContext> {

    protected KafkaEventPublisherHandler(final KafkaImsEventPublisher kafkaImsEventPublisher) {

        super(kafkaImsEventPublisher);
    }
}
