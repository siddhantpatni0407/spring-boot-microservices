package com.sid.app.controller;

import com.sid.app.constant.AppConstants;
import com.sid.app.model.Response;
import com.sid.app.service.KafkaService;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * @author Siddhant Patni
 */
@RestController
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping(value = AppConstants.KAFKA_TOPIC_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> createTopic(@RequestParam(value = "topicName") String topicName,
                                                      @RequestParam(value = "partition") int partition) {
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

    @PostMapping(value = AppConstants.STOP_ZOOKEEPER_SERVER_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> stopZookeeperServer() {
        if (log.isInfoEnabled()) {
            log.info("stopZookeeperServer() : Stop Zookeeper Server- START");
        }
        return kafkaService.stopZookeeper()
                .flatMap(responseResponseEntity -> {
                    if (log.isInfoEnabled()) {
                        log.info("stopZookeeperServer() : Stop Zookeeper Server API. Response -> {}",
                                ApplicationUtils.getJSONString(responseResponseEntity));
                        log.info("stopZookeeperServer() : Stop Zookeeper Server - END");
                    }
                    return Mono.just(responseResponseEntity);
                });
    }

    @PostMapping(value = AppConstants.STOP_KAFKA_SERVER_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> stopKafkaServer() {
        if (log.isInfoEnabled()) {
            log.info("stopKafkaServer() : Stop Kafka Server - START");
        }
        return kafkaService.stopKafka()
                .flatMap(responseResponseEntity -> {
                    if (log.isInfoEnabled()) {
                        log.info("stopKafkaServer() : Stop Kafka Server API. Response -> {}",
                                ApplicationUtils.getJSONString(responseResponseEntity));
                        log.info("stopKafkaServer() : Stop Kafka Server - END");
                    }
                    return Mono.just(responseResponseEntity);
                });
    }

}