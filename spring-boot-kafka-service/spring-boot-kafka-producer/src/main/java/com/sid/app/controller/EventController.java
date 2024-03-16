package com.sid.app.controller;

import com.sid.app.constant.AppConstants;
import com.sid.app.model.Customer;
import com.sid.app.model.Response;
import com.sid.app.service.KafkaMessagePublisher;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Siddhant Patni
 */
@RestController
@Slf4j
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;

    @GetMapping(value = AppConstants.KAFKA_PUBLISH_MESSAGE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response>> publishMessage(@RequestParam(value = "message") String message) {
        try {
            if (log.isInfoEnabled()) {
                log.info("sendEvents() : Publish Message - START");
            }
            return publisher.sendMessageToTopic(message)
                    .flatMap(response -> {
                        if (log.isInfoEnabled()) {
                            log.info("publishMessage() : Publish Event API. Request param-> {} and response -> {}",
                                    message, ApplicationUtils.getJSONString(response));
                            log.info("sendEvents() : Publish Message - END");
                        }
                        return Mono.just(ResponseEntity.ok(response));
                    });
        } catch (Exception ex) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }
    }

    @PostMapping(value = AppConstants.KAFKA_PUBLISH_MESSAGE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response> sendEvents(@RequestBody Customer request) {
        if (log.isInfoEnabled()) {
            log.info("sendEvents() : Publish Event - START");
        }
        return publisher.sendEventsToTopic(request)
                .flatMap(response -> {
                    if (log.isInfoEnabled()) {
                        log.info("sendEvents() : Publish Event API. request -> {} and response -> {}",
                                ApplicationUtils.getJSONString(request), ApplicationUtils.getJSONString(response));
                        log.info("sendEvents() : Publish Event - END");
                    }
                    return Mono.just(response);
                });
    }

}