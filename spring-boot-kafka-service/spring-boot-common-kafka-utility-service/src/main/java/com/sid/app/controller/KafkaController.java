package com.sid.app.controller;

import com.sid.app.constant.AppConstants;
import com.sid.app.model.Response;
import com.sid.app.service.KafkaService;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * @author Siddhant Patni
 */
@RestController
@Slf4j
@CrossOrigin
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping(value = AppConstants.KAFKA_TOPIC_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> createTopic(@RequestParam(value = "topicName") String topicName,
                                                      @RequestParam(value = "partition", required = false) Integer partition) {
        if (log.isInfoEnabled()) {
            log.info("createTopic() : Create Topic - START");
        }
        return kafkaService.createTopic(topicName, partition)
                .flatMap(responseResponseEntity -> {
                    if (log.isInfoEnabled()) {
                        log.info("createTopic() : Create Topic API. Request param => topicName -> {}, partition -> {} and Response -> {}",
                                topicName, partition, ApplicationUtils.getJSONString(responseResponseEntity));
                        log.info("createTopic() : Create Topic - END");
                    }
                    return Mono.just(responseResponseEntity);
                });
    }

    @GetMapping(value = AppConstants.KAFKA_TOPIC_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Collection<String>>> getAllTopics() {
        if (log.isInfoEnabled()) {
            log.info("getAllTopics() : Get All Topics - START");
        }
        return kafkaService.getAllTopics()
                .flatMap(allTopics -> {
                    if (log.isInfoEnabled()) {
                        log.info("getAllTopics() : Get all Topics API. Response -> {}",
                                ApplicationUtils.getJSONString(allTopics));
                        log.info("getAllTopics() : Get All Topics - END");
                    }
                    return Mono.just(allTopics);
                });

    }

    @DeleteMapping(value = AppConstants.KAFKA_TOPIC_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> deleteTopic(@RequestParam(value = "topicName") String topicName) {
        if (log.isInfoEnabled()) {
            log.info("deleteTopic() : Delete Topic - START");
        }
        return kafkaService.deleteTopic(topicName)
                .flatMap(responseResponseEntity -> {
                    if (log.isInfoEnabled()) {
                        log.info("deleteTopic() : Delete Topic API. Request param => topicName -> {} and Response -> {}",
                                topicName, ApplicationUtils.getJSONString(responseResponseEntity));
                        log.info("deleteTopic() : Delete Topic - END");
                    }
                    return Mono.just(responseResponseEntity);
                });
    }

    @PostMapping(value = AppConstants.START_KAFKA_SERVERS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> startServers() {
        if (log.isInfoEnabled()) {
            log.info("startServers() : Start Zookeeper and Kafka Server - START");
        }
        return kafkaService.startServers()
                .flatMap(responseResponseEntity -> {
                    if (log.isInfoEnabled()) {
                        log.info("startServers() : Start Zookeeper and Kafka Server API. Response -> {}",
                                ApplicationUtils.getJSONString(responseResponseEntity));
                        log.info("startServers() : Start Zookeeper and Kafka Server - END");
                    }
                    return Mono.just(responseResponseEntity);
                });
    }

    @PostMapping(value = AppConstants.STOP_KAFKA_SERVERS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> stopKafkaServers() {
        if (log.isInfoEnabled()) {
            log.info("stopKafkaServers() : Stop Zookeeper and Kafka Server - START");
        }
        return kafkaService.stopKafkaServers()
                .flatMap(responseResponseEntity -> {
                    if (log.isInfoEnabled()) {
                        log.info("stopKafkaServers() : Stop Zookeeper and Kafka Server API. Response -> {}",
                                ApplicationUtils.getJSONString(responseResponseEntity));
                        log.info("stopKafkaServers() : Stop Zookeeper and Kafka Server - END");
                    }
                    return Mono.just(responseResponseEntity);
                });
    }

    @DeleteMapping(value = AppConstants.DELETE_KAFKA_LOGS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> deleteKafkaLogs() {
        if (log.isInfoEnabled()) {
            log.info("deleteKafkaLogs() : Delete Kafka Logs - START");
        }
        return kafkaService.deleteKafkaLogs()
                .flatMap(responseResponseEntity -> {
                    if (log.isInfoEnabled()) {
                        log.info("deleteKafkaLogs() : Delete Kafka Logs API. Response -> {}",
                                ApplicationUtils.getJSONString(responseResponseEntity));
                        log.info("deleteKafkaLogs() : Delete Kafka Logs - END");
                    }
                    return Mono.just(responseResponseEntity);
                });
    }

}