package org.am.library.events;

import org.am.library.publishing.kafka.KafkaPublishContext;

public interface ImsEventPublisher {

    void publish(KafkaPublishContext publishContext) throws Exception;
}
