package com.sid.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.app.config.AppProperties;
import com.sid.app.constant.AppConstants;
import com.sid.app.exception.ServiceException;
import lombok.extern.slf4j.XSlf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
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

    /**
     * To get decrypted value.
     *
     * @param plainValue String
     * @return String
     */
    public String getEncryptedValue(String plainValue) {
        return getJasyptValue(true, plainValue);
    }

    /**
     * To get decrypted value.
     *
     * @param encryptedValue String
     * @return String
     */
    public String getDecryptedValue(String encryptedValue) {
        return getJasyptValue(false, encryptedValue);
    }

    /**
     * This method is used encrpt or decrypt given plain value.
     *
     * @param encrypt    boolean
     * @param inputValue String
     * @return encrypted or decrypted value
     */
    private String getJasyptValue(boolean encrypt, String inputValue) {

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(properties.getEncryptionKey());
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setAlgorithm(AppConstants.JASYPT_ALGORITHM);
        config.setSaltGeneratorClassName(AppConstants.JASYPT_SALT_GENERATOR_CLASS_NAME);
        encryptor.setConfig(config);

        String outputValue;
        if (encrypt) {
            outputValue = encryptor.encrypt(inputValue);
        } else {
            outputValue = encryptor.decrypt(inputValue);
        }

        if (log.isDebugEnabled()) {
            log.debug("getJasyptValue() : encrypt -> {}, inputValue -> {}, outputValue -> {}", encrypt, inputValue, outputValue);
        }
        return outputValue;
    }

}