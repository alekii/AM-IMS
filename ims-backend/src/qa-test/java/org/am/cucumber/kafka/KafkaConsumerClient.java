package org.am.cucumber.kafka;

import org.am.cucumber.kafka.helpers.KafkaHelpers;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.junit.Assert;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class KafkaConsumerClient {

    private static final KafkaConsumerClient INSTANCE = new KafkaConsumerClient();

    private static final int MAX_POLL_RETRIES;

    private Map<String, KafkaConsumer> kafkaConsumers;

    private Map<String, Map<Integer, Long>> topicsPartitionsOffset;

    private ConsumerRecord<String, String> kafkaConsumedRecord;

    private String kafkaConsumerTopic;

    private final static List<Integer> pollTimeouts = List.of(200, 300, 400, 500, 600, 700, 800, 900, 1000);

    public KafkaConsumerClient() {

        this.kafkaConsumerTopic = null;
        this.kafkaConsumedRecord = null;
        this.kafkaConsumers = new HashMap<>();
        this.topicsPartitionsOffset = new HashMap<>();
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

    public Map<String, Map<Integer, Long>> getTopicsPartitionsOffset() {

        return this.topicsPartitionsOffset;
    }

    public void setKafkaConsumerTopic(final String topic) {

        this.verifyTopicExists(topic);
        this.createKafkaConsumer(topic);
        this.kafkaConsumerTopic = topic;
    }

    public void verifyTopicExists(final String topic) {

        Assert.assertTrue("topic not found", this.getKafkaAdmin().retryTopicExists(topic));
    }

    private void createKafkaConsumer(String topic) {

        if (!this.kafkaConsumers.containsKey(topic)) {
            Properties properties = this.getKafkaHelpers().getConsumerProperties();
            String consumerId = MessageFormat.format("{0}-{1}-{2}", topic, "consumer-id", UUID.randomUUID().toString());
            String consumerGroupId = MessageFormat.format("{0}-{1}-{2}", topic, "consumer-group", UUID.randomUUID().toString());
            properties.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerId);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
            this.getTopicsPartitionsOffset().put(topic, new HashMap<>());
            KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(properties);
            this.assignKafkaPartitionsToConsumer(consumer, topic);
            this.kafkaConsumers.put(topic, consumer);
        }
    }

    public Optional<Header> getKafkaHeaderRecord(final String headerKey) {

        Assert.assertTrue("No Kafka header with key", Objects.nonNull(headerKey));
        Assert.assertTrue("No consumer record present", Objects.nonNull(this.getKafkaConsumedRecord()));
        return StreamSupport
                .stream(this.getKafkaConsumedRecord().headers().spliterator(), false)
                .filter(v -> v.key().equals(headerKey))
                .findFirst();
    }

    public void seekConsumerToTheEnd(final String topic) {

        this.verifyTopicExists(topic);
        List<TopicPartition> topicPartitions = this.getTopicPartitionsForTopic(topic);
        this.getKafkaConsumer(topic).seekToEnd(topicPartitions);
        this.getKafkaConsumer(topic).position(topicPartitions.get(0));
    }

    private List<TopicPartition> getTopicPartitionsForTopic(final String topic) {

        List<PartitionInfo> partitionInfos = this.getKafkaConsumer(topic).partitionsFor(topic);
        List<TopicPartition> topicPartitions = partitionInfos.stream()
                .map(partitionInfo -> new TopicPartition(topic, partitionInfo.partition()))
                .collect(Collectors.toList());
        return topicPartitions;
    }

    private KafkaConsumer getKafkaConsumer(final String topic) {

        if (!this.kafkaConsumers.containsKey(topic) || !topic.equals(this.kafkaConsumerTopic)) {
            throw new IllegalStateException("consumer not registered or topic mismatch");
        }
        return this.kafkaConsumers.get(topic);
    }

    private void assignKafkaPartitionsToConsumer(final KafkaConsumer<String, Object> consumer, String topic) {

        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        List<TopicPartition> topicPartitions = partitionInfos.stream()
                .map(partitionInfo -> new TopicPartition(topic, partitionInfo.partition()))
                .collect(Collectors.toList());
        consumer.assign(topicPartitions);
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
        } while (getMessage && retryCount.get() < MAX_POLL_RETRIES - 1);
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

    private KafkaHelpers getKafkaHelpers() {

        return KafkaHelpers.getInstance();
    }

    private KafkaAdmin getKafkaAdmin() {

        return KafkaAdmin.getInstance();
    }

    static {

        MAX_POLL_RETRIES = pollTimeouts.size();
    }
}
