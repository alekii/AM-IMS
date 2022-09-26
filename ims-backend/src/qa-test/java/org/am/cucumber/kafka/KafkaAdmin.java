package org.am.cucumber.kafka;

import org.am.cucumber.kafka.helpers.KafkaHelpers;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DescribeTopicsOptions;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.errors.TopicExistsException;
import org.junit.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class KafkaAdmin {

    private static final KafkaAdmin INSTANCE = new KafkaAdmin();

    private final KafkaAdminClient kafkaAdminClient;

    private static final int MAX_TOPIC_RETRIES;

    private final static int[] topicRetries = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500};

    public KafkaAdmin() {

        this.kafkaAdminClient = this.createAdminClient();
    }

    public static KafkaAdmin getInstance() {

        return INSTANCE;
    }

    public boolean createTopic(final String topic) {

        Assert.assertTrue("topic is not defined", Objects.nonNull(topic));
        int partitions = 1;
        short replicationFactor = 1;
        NewTopic newTopic = new NewTopic(topic, partitions, replicationFactor);
        CreateTopicsResult result = this.kafkaAdminClient.createTopics(Collections.singleton(newTopic));
        try {
            KafkaFuture<Void> future = result.values().get(topic);
            future.get(5, SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            return e.getCause() instanceof TopicExistsException;
        }
        return ((KafkaFuture<Void>) result.values().get(topic)).isDone();
    }

    private KafkaAdminClient createAdminClient() {

        Properties properties = this.getKafkaHelpers().getAdminProperties();
        return (KafkaAdminClient) KafkaAdminClient.create(properties);
    }

    private int[] getRetries() {

        return this.topicRetries;
    }

    boolean retryTopicExists(final String topic) {

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
        DescribeTopicsResult topicsResult = this.kafkaAdminClient.describeTopics(Collections.singleton(topic), topicsOptions);
        try {
            List<TopicPartitionInfo> topicPartitionInfoList = topicsResult.values().get(topic).get(5, SECONDS).partitions();
            return !topicPartitionInfoList.isEmpty();
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            //TODO add logger
            throw new IllegalStateException(e);
        }
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

    private KafkaHelpers getKafkaHelpers() {

        return KafkaHelpers.getInstance();
    }

    static {

        MAX_TOPIC_RETRIES = topicRetries.length;
    }
}
