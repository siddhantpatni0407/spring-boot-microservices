package com.sid.app.producer;

import com.sid.app.config.AppProperties;
import com.sid.app.model.Customer;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

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
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishEvents(Customer customer) {
        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(properties.getKafkaTopic(), customer);
        CompletableFuture<SendResult<String, Object>> completableFuture = new CompletableFuture<>();

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                completableFuture.complete(result);
                if (log.isInfoEnabled()) {
                    log.info("publishEvents() : Sent event -> [{}] with offset -> [{}]", ApplicationUtils.getJSONString(customer), result.getRecordMetadata().offset());
                }
            }

            @Override
            public void onFailure(Throwable ex) {
                completableFuture.completeExceptionally(ex);
                if (log.isErrorEnabled()) {
                    log.error("publishEvents() : Unable to send event -> [{}] due to error -> {}", customer.toString(), ex.getMessage());
                }
            }
        });
    }

}