package org.am.library.publishing.kafka;

import org.am.library.events.ImsEventPublisher;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class KafkaImsEventPublisher implements ImsEventPublisher<KafkaPublishContext> {

    private final KafkaTemplate<String, Object> template;

    public KafkaImsEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {

        this.template = kafkaTemplate;
    }

    @Override
    public void publish(KafkaPublishContext publishContext) {

        final Object payload = Objects.nonNull(publishContext.getPayload()) ?
                publishContext.getPayload() :
                publishContext.getImsEvent().getData();

        final ProducerRecord<String, Object> producerRecord =
                new ProducerRecord<>(publishContext.getTopic(), payload);

        String eventName = publishContext.getImsEvent().getEventName().name();

        producerRecord.headers().add("event", eventName.getBytes(StandardCharsets.UTF_8));

        ListenableFuture<SendResult<String, Object>> future = template.send(producerRecord);
        future.addCallback(new KafkaSendCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                //TODO add logger
            }

            @Override
            public void onFailure(KafkaProducerException ex) {
                //TODO add logger
            }
        });
    }
}
