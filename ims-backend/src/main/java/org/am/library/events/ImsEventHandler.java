package org.am.library.events;

import org.am.library.publishing.kafka.KafkaPublishContext;

public abstract class ImsEventHandler {

    protected ImsEventPublisher imsEventPublisher;

    protected ImsEventHandler(final ImsEventPublisher imsEventPublisher) {

        this.imsEventPublisher = imsEventPublisher;
    }

    protected void publish(KafkaPublishContext c) {

        try {
            imsEventPublisher.publish(c);
        } catch (Exception ignored) {

        }
    }
}
