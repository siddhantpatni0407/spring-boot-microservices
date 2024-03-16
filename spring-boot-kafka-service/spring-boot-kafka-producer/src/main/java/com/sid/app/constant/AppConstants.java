package com.sid.app.constant;

/**
 * @author Siddhant Patni
 * Utility class containing constants used for defining endpoints.
 */
public class AppConstants {

    /**
     * Constant for the Kafka publish message endpoint.
     */
    public static final String KAFKA_PUBLISH_MESSAGE_ENDPOINT = "/api/v1/kafka-service/publish";
    public static final String KAFKA_TOPIC_ENDPOINT = "/api/v1/kafka-service/kafka/topic";
    public static final String STOP_ZOOKEEPER_SERVER_ENDPOINT = "/api/v1/kafka-service/zookeeper/stop";
    public static final String STOP_KAFKA_SERVER_ENDPOINT = "/api/v1/kafka-service/kafka/stop";

}