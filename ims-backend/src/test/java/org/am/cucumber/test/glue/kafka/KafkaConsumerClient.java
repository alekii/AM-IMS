package org.am.cucumber.test.glue.kafka;

import org.am.cucumber.test.glue.utils.Constants;
import org.apache.kafka.clients.admin.DescribeTopicsOptions;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Assert;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

public class KafkaConsumerClient {

    private static final KafkaConsumerClient INSTANCE = new KafkaConsumerClient();

    private static final int MAX_TOPIC_RETRIES;

    private static final int MAX_POLL_RETRIES;

    private String kafkaConsumerTopic;

    private Map<String, KafkaConsumer> kafkaConsumers;

    private Map<String, Map<Integer, Long>> topicsPartitionsOffset;

    private ConsumerRecord<String, String> kafkaConsumedRecord;

    private final KafkaAdminClient kafkaAdminClient;

    private final static List<Integer> pollTimeouts = List.of(200, 300, 400, 500, 600, 700, 800, 900, 1000);

    private final static int[] topicRetries = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500};

    public KafkaConsumerClient() {

        this.kafkaAdminClient = this.createAdminClient();
        this.kafkaConsumerTopic = null;
        this.kafkaConsumedRecord = null;
        this.kafkaConsumers = new HashMap<>();
        this.topicsPartitionsOffset = new HashMap<>();
    }

    static {
        MAX_POLL_RETRIES = topicRetries.length;
        MAX_TOPIC_RETRIES = pollTimeouts.size();
    }

    private KafkaConsumer getKafkaConsumer(final String topic) {

        if (!this.kafkaConsumers.containsKey(topic) || !topic.equals(this.kafkaConsumerTopic)) {
            throw new IllegalStateException("consumer not registered or topic mismatch");
        }
        return this.kafkaConsumers.get(topic);
    }

    public Map<String, Map<Integer, Long>> getTopicsPartitionsOffset() {

        return this.topicsPartitionsOffset;
    }

    private KafkaAdminClient createAdminClient() {

        Properties properties = new Properties();
        String bootstrapServers = Constants.getKafkaBootstrapServers();
        Assert.assertTrue("Kafka bootstrap servers is not defined", Objects.nonNull(bootstrapServers));
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("client.id", "client-1");
        return (KafkaAdminClient) KafkaAdminClient.create(properties);
    }

    public static KafkaConsumerClient getInstance() {

        return INSTANCE;
    }

    public String getKafkaConsumerTopic() {

        return this.kafkaConsumerTopic;
    }

    public ConsumerRecord<String, String> getKafkaConsumedRecord() {

        return this.kafkaConsumedRecord;
    }

    private int[] getRetries() {

        return this.topicRetries;
    }

    public void setKafkaConsumerTopic(String topic) {

        this.verifyTopicExists(topic);
        this.createKafkaConsumer(topic);
        this.kafkaConsumerTopic = topic;
    }

    private void verifyTopicExists(String topic) {

        Assert.assertTrue("topic not found", this.retryTopicExists(topic));
    }

    private boolean retryTopicExists(String topic) {

        int noOfAttemptedRetries = 0;
        int waitingTime = 0;
        boolean topicPresent = false;
        try {
            while (noOfAttemptedRetries < MAX_TOPIC_RETRIES && !topicPresent) {
                if (topicPresent) {
                    break;
                }
                topicPresent = this.topicExists(topic);
                waitingTime = this.getRetries()[noOfAttemptedRetries];
                Thread.sleep((long) (waitingTime));
                noOfAttemptedRetries++;
            }
        } catch (InterruptedException e) {
            //TODO add logger
            throw new IllegalStateException(e);
        }
        return topicPresent;
    }

    private boolean topicExists(String topic) {

        Set<String> topics = this.getTopics();
        if (topics.contains(topic)) {

            //check if partitions are present
            return this.checkPartitionsInTopic(topic);
        }
        return false;
    }

    private boolean checkPartitionsInTopic(String topic) {

        DescribeTopicsOptions topicsOptions = new DescribeTopicsOptions();
        topicsOptions.timeoutMs(5000);
        DescribeTopicsResult topicsResult = kafkaAdminClient.describeTopics(Collections.singleton(topic), topicsOptions);
        try {
            List<TopicPartitionInfo> topicPartitionInfoList = topicsResult.values().get(topic).get(5, SECONDS).partitions();
            return !topicPartitionInfoList.isEmpty();
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            //TODO add logger
            throw new IllegalStateException(e);
        }
    }

    private void createKafkaConsumer(String topic) {

        if (!this.kafkaConsumers.containsKey(topic)) {
            Properties properties = this.getConsumerProperties();
            String ConsumerConfigId = MessageFormat.format("{0}-{1}-{2}", topic, "consumer", UUID.randomUUID().toString());
            properties.put(ConsumerConfig.CLIENT_ID_CONFIG, ConsumerConfigId);
            this.getTopicsPartitionsOffset().put(topic, new HashMap<>());
            KafkaConsumer<String, Object> consumer = new KafkaConsumer<String, Object>(properties);
            this.kafkaConsumers.put(topic, consumer);
        }
    }

    private Properties getConsumerProperties() {

        String bootstrapServers = Constants.getKafkaBootstrapServers();
        Assert.assertTrue("bootstrap servers is not defined", Objects.nonNull(bootstrapServers));
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return properties;
    }

    private Set<String> getTopics() {

        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();

        ListTopicsResult listTopicsResult = this.kafkaAdminClient.listTopics(listTopicsOptions);

        try {
            return listTopicsResult.names().get(5, SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            //TODO add logger
            throw new IllegalStateException(e);
        }
    }

    public boolean getConsumedMessage(final String topic) {

        AtomicInteger retryCount = new AtomicInteger(0);
        boolean getMessage = true;
        boolean messageFound;
        do {
            seekConsumerToOffset(topic);
            Duration duration = Duration.ofMillis((long) (pollTimeouts.get(retryCount.get())));
            messageFound = this.consumeMessage(topic, duration);
            if (messageFound) {
                // update offset
                this.updateTopicPartitionsOffset(topic, this.kafkaConsumedRecord.partition(), this.kafkaConsumedRecord.offset() + 1L);
                getMessage = false;
            } else {
                retryCount.incrementAndGet();
            }
        } while (getMessage && retryCount.get() < MAX_POLL_RETRIES);
        return messageFound;
    }

    private void updateTopicPartitionsOffset(final String topic, final int partitionId, final long offset) {

        if (!this.getTopicsPartitionsOffset().containsKey(topic)) {
            throw new IllegalStateException(String.format("Topic %s is not available", topic));
        } else {
            TopicPartition topicPartition = new TopicPartition(topic, partitionId);
            Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
            OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(offset);
            offsets.put(topicPartition, offsetAndMetadata);
            this.getTopicsPartitionsOffset().get(topic).put(partitionId, offset);
            this.getKafkaConsumer(topic).commitSync(offsets);
        }
    }

    private boolean consumeMessage(final String topic, final Duration duration) {

        ConsumerRecords<String, String> consumerRecords = this.getKafkaConsumer(topic).poll(duration);
        if (consumerRecords.isEmpty()) {
            return false;
        } else {
            Long timestamp = null;

            for (ConsumerRecord<String, String> record : consumerRecords) {
                if (Objects.isNull(timestamp)) {
                    timestamp = record.timestamp();
                    this.kafkaConsumedRecord = record;
                } else if (timestamp > record.timestamp()) {
                    timestamp = record.timestamp();
                    this.kafkaConsumedRecord = record;
                }
            }

            return true;
        }
    }

    private void seekConsumerToOffset(final String topic) {

        if (!this.getTopicsPartitionsOffset().containsKey(topic)) {
            throw new IllegalStateException(String.format("Topic %s is not available", topic));
        } else {
            for (Map.Entry<Integer, Long> partitions : this.getTopicsPartitionsOffset().get(topic).entrySet()) {
                int partitionId = partitions.getKey();
                TopicPartition partition = new TopicPartition(topic, partitionId);
                long offset = partitions.getValue();
                this.getKafkaConsumer(topic).seek(partition, offset);
            }
        }
    }
}
