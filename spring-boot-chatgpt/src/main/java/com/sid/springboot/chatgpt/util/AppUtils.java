package com.sid.springboot.chatgpt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.springboot.chatgpt.config.AppProperties;
import com.sid.springboot.chatgpt.exception.ServiceException;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Consumer;

/**
 * The type App utils.
 *
 * @author Siddhant Patni
 */
@Component
@XSlf4j
public class AppUtils {

    @Autowired
    private AppProperties properties;

    /**
     * Gets json string.
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return the json string
     */
    public static <T> String getJSONString(T object) {
        if (object != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(object);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("Error occurred [{}] while converting to string [{}]", e.getMessage(), object);
                }
            }
        }
        return "";
    }

    /**
     * Gets http headers.
     *
     * @return the http headers
     */
    public Consumer<HttpHeaders> getHttpHeaders() {
        return httpHeaders -> {
            httpHeaders.setBearerAuth(properties.getToken());
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        };
    }

    /**
     * Gets retry.
     *
     * @return the retry
     */
    public Retry getRetry() {
        return Retry.backoff(properties.getMaxRetry(), Duration.ofSeconds(properties.getDelay())).filter(throwable ->
                throwable instanceof ServiceException && ((ServiceException) throwable).getStatus().is5xxServerError()).onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
            ServiceException serviceException = (ServiceException) retrySignal.failure();
            throw new ServiceException(serviceException.getStatus(), serviceException.getStatus().value(), serviceException.getReason());
        });
    }

    /**
     * Read value t.
     *
     * @param <T>       the type parameter
     * @param content   the content
     * @param valueType the value type
     * @return the t
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Error occurred [{}] while reading from string [{}]", e.getMessage(), content);
            }
        }
        return null;
    }

}