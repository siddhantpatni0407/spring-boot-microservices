package com.sid.app.service;

import com.sid.app.config.AppProperties;
import com.sid.app.model.Customer;
import com.sid.app.model.Response;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * @author Siddhant Patni
 */
@Service
@Slf4j
public class KafkaMessagePublisher {

    @Autowired
    private AppProperties properties;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public Mono<Response> sendMessageToTopic(String message) {
        Response response = Response.builder().build();
        ListenableFuture<SendResult<String, Object>> listenableFuture = template.send(properties.getKafkaTopic(), message);
        CompletableFuture<SendResult<String, Object>> completableFuture = new CompletableFuture<>();

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                completableFuture.complete(result);
                log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
                response.setStatus("Message sent to Kafka successfully. Offset -> " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable exception) {
                completableFuture.completeExceptionally(exception);
                log.error("Unable to send message=[{}] due to {}", message, exception.getMessage());
                response.setStatus("Unable to send message due to " + exception.getMessage());
            }
        });
        return Mono.just(response);
    }

    public Mono<Response> sendEventsToTopic(Customer customer) {
        Response response = Response.builder().build();
        ListenableFuture<SendResult<String, Object>> listenableFuture = template.send(properties.getKafkaTopic(), customer);
        CompletableFuture<SendResult<String, Object>> completableFuture = new CompletableFuture<>();

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                completableFuture.complete(result);
                log.info("Sent event=[{}] with offset=[{}]", ApplicationUtils.getJSONString(customer), result.getRecordMetadata().offset());
                response.setStatus("Event published successfully. Offset -> " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                completableFuture.completeExceptionally(ex);
                log.error("Unable to send event=[{}] due to {}", customer.toString(), ex.getMessage());
                response.setStatus("Unable to send event due to " + ex.getMessage());
            }
        });
        return Mono.just(response);
    }

}