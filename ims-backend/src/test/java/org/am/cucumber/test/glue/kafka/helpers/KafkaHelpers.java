package org.am.cucumber.test.glue.kafka.helpers;

import org.am.cucumber.test.glue.utils.Constants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Assert;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Objects;
import java.util.Properties;

public class KafkaHelpers {

    private static final KafkaHelpers INSTANCE = new KafkaHelpers();

    public static KafkaHelpers getInstance() {

        return INSTANCE;
    }

    public Properties getConsumerProperties() {

        String bootstrapServers = Constants.getKafkaBootstrapServers();
        Assert.assertTrue("bootstrap servers is not defined", Objects.nonNull(bootstrapServers));
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return properties;
    }

    public Properties getAdminProperties() {

        Properties properties = new Properties();
        String bootstrapServers = Constants.getKafkaBootstrapServers();
        Assert.assertTrue("Kafka bootstrap servers is not defined", Objects.nonNull(bootstrapServers));
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("client.id", "client-1");
        return properties;
    }
}
