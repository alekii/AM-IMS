package org.am.library.publishing.kafka;

import org.am.library.events.ImsEventPublisher;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class KafkaImsEventPublisher implements ImsEventPublisher<KafkaPublishContext> {

    @Override
    public void publish(KafkaPublishContext publishContext) {

        final Object payload = Objects.nonNull(publishContext.getPayload()) ?
                publishContext.getPayload() :
                publishContext.getImsEvent().getData();

        final KafkaProducer<String, Object> producer = new KafkaProducer<>(producerConfig());

        final ProducerRecord<String, Object> producerRecord =
                new ProducerRecord<>(publishContext.getTopic(), payload);

        producer.send(producerRecord);
    }

    public Map<String, Object> producerConfig() {

        HashMap<String, Object> properties = new HashMap();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKA_BOOTSTRAP_SERVERS"));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }
}
