package org.am.library.events;

public abstract class ImsEventHandler<KafkaPublishContext> {

    protected ImsEventPublisher<KafkaPublishContext> imsEventPublisher;

    protected ImsEventHandler(final ImsEventPublisher<KafkaPublishContext> imsEventPublisher) {

        this.imsEventPublisher = imsEventPublisher;
    }

    protected void publish(KafkaPublishContext c) {

        try {
            imsEventPublisher.publish(c);
        } catch (Exception ignored) {

        }
    }
}
