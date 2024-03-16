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

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
                if (log.isErrorEnabled()) {
                    log.error("Exception occurred : {}", e.getMessage());
                }
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
                if (log.isErrorEnabled()) {
                    log.error("Exception occurred : {}", e.getMessage());
                }
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
                if (log.isErrorEnabled()) {
                    log.error("Exception occurred : {}", e.getMessage());
                }
                // Return an empty collection in case of error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ArrayList<>());
            }
        });
    }

    public Mono<ResponseEntity<Response>> startServers() {
        Response response = Response.builder().build();
        try {
            ProcessBuilder zookeeperProcessBuilder = new ProcessBuilder();
            zookeeperProcessBuilder.command("cmd.exe", "/c", "start", "cmd.exe", "/k", "call",
                    appProperties.getKafkaInstallationDirectory() + "\\zookeeper-server-start.bat",
                    appProperties.getZookeeperConfigPath());
            Process zookeeperProcess = zookeeperProcessBuilder.start();

            ProcessBuilder kafkaProcessBuilder = new ProcessBuilder();
            kafkaProcessBuilder.command("cmd.exe", "/c", "start", "cmd.exe", "/k", "call",
                    appProperties.getKafkaInstallationDirectory() + "\\kafka-server-start.bat",
                    appProperties.getKafkaConfigPath());
            Process kafkaProcess = kafkaProcessBuilder.start();

            // Check if both processes started successfully
            if (zookeeperProcess.isAlive() && kafkaProcess.isAlive()) {
                response.setStatus("Zookeeper and Kafka servers started successfully.");
            } else {
                response.setErrorMessage("Failed to start Zookeeper or Kafka server.");
            }
            return Mono.just(ResponseEntity.ok(response));
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("startServers() : Exception occurred : {}", e.getMessage());
            }
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Failed to start the zookeeper and kafka server with exception : ", e.getMessage())));
        }
    }

    public Mono<ResponseEntity<Response>> stopZookeeper() {
        Response response = Response.builder().build();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", appProperties.getKafkaInstallationDirectory() + "\\zookeeper-server-stop.bat");
            //processBuilder.command("sh", "-c", appProperties.getKafkaInstallationDirectory().concat("/zookeeper-server-stop.sh"));
            if (log.isInfoEnabled()) {
                log.info("stopZookeeper() : path : {}", appProperties.getKafkaInstallationDirectory().concat("\\zookeeper-server-stop.bat"));
            }
            Process process = processBuilder.start();
            // Wait for the process to terminate
            process.waitFor(30, TimeUnit.SECONDS);
            // Check if the process has exited normally
            if (process.exitValue() == 0) {
                response.setStatus("Zookeeper server stopped successfully.");
            } else {
                response.setErrorMessage("Failed to stop Zookeeper server.");
            }
            return Mono.just(ResponseEntity.ok(response));
        } catch (IOException | InterruptedException e) {
            if (log.isErrorEnabled()) {
                log.error("stopZookeeper() : Exception occurred : {}", e.getMessage());
            }
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Failed to stop the zookeeper server with exception : ", e.getMessage())));
        }
    }

    public Mono<ResponseEntity<Response>> stopKafka() {
        Response response = Response.builder().build();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", appProperties.getKafkaInstallationDirectory() + "\\kafka-server-stop.bat");
            //processBuilder.command("sh", "-c", appProperties.getKafkaInstallationDirectory().concat("/kafka-server-stop.sh"));
            if (log.isInfoEnabled()) {
                log.info("stopKafka() : path : {}", appProperties.getKafkaInstallationDirectory().concat("\\kafka-server-stop.bat"));
            }
            Process process = processBuilder.start();
            // Wait for the process to terminate
            process.waitFor(30, TimeUnit.SECONDS);
            // Check if the process has exited normally
            if (process.exitValue() == 0) {
                response.setStatus("Kafka server stopped successfully.");
            } else {
                response.setErrorMessage("Failed to stop Kafka server.");
            }
            return Mono.just(ResponseEntity.ok(response));
        } catch (IOException | InterruptedException e) {
            if (log.isErrorEnabled()) {
                log.error("stopKafka() : Exception occurred : {}", e.getMessage());
            }
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Failed to stop the kafka server with exception : ", e.getMessage())));
        }
    }

}