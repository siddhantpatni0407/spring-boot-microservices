package com.sid.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type App properties.
 *
 * @author Siddhant Patni
 */
@Data
@Component
public class AppProperties {

    @Value("${encryptionKey}")
    private String encryptionKey;

    @Value("${webclient.config.memory-buffer-size}")
    private int bufferSize;

    @Value("${webclient.config.timeout}")
    private int timeout;

    @Value("${webclient.config.retry.max-retry}")
    private int maxRetry;

    @Value("${webclient.config.retry.delay}")
    private int delay;

    @Value("${spring.webflux.multipart.max-files-allowed}")
    private int maxFilesAllowed;

}