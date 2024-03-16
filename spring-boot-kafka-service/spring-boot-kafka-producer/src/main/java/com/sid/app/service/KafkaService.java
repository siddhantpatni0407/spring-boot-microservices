package com.sid.app.service;

import com.sid.app.config.AppProperties;
import com.sid.app.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Siddhant Patni
 */
@Service
@Slf4j
public class KafkaService {

    @Autowired
    private AppProperties appProperties;

    public Mono<ResponseEntity<Response>> createTopic(String topicName, int partition) {
        return Mono.fromCallable(() -> {
            Properties properties = new Properties();
            properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, appProperties.getBootstrapServers());

            try (AdminClient adminClient = AdminClient.create(properties)) {
                NewTopic newTopic = new NewTopic(topicName, partition, (short) 1);
                adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
                return ResponseEntity.ok(new Response("Topic -[" + topicName + "]  created successfully", null));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response("Failed to create topic", e.getMessage()));
            }
        });
    }

    public Mono<ResponseEntity<Response>> deleteTopic(String topicName) {
        return Mono.fromCallable(() -> {
            Properties properties = new Properties();
            properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, appProperties.getBootstrapServers());

            try (AdminClient adminClient = AdminClient.create(properties)) {
                DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singletonList(topicName));
                deleteTopicsResult.all().get();
                return ResponseEntity.ok(new Response("Topic -[" + topicName + "] deleted successfully", null));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response("Failed to delete topic", e.getMessage()));
            }
        });
    }

    public Mono<ResponseEntity<Collection<String>>> getAllTopics() {
        return Mono.fromCallable(() -> {
            Properties properties = new Properties();
            properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, appProperties.getBootstrapServers());

            try (AdminClient adminClient = AdminClient.create(properties)) {
                ListTopicsResult topicsResult = adminClient.listTopics();
                Set<String> topics = topicsResult.names().get();
                return ResponseEntity.ok(new ArrayList<>(topics));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                // Return an empty collection in case of error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ArrayList<>());
            }
        });
    }

}