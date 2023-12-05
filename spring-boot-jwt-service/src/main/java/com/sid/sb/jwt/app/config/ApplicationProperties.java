package com.sid.sb.jwt.app.config;

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
public class ApplicationProperties {

    @Value("${jwt.secret}")
    private String jwtSecret;

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

}