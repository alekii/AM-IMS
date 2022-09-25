package org.am.cucumber.utils;

public class Constants {

    public static final String FORWARD_SLASH = "/";

    private static String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";

    public Constants() {

    }

    public static String getKafkaBootstrapServers() {

        return KAFKA_BOOTSTRAP_SERVERS;
    }
}
